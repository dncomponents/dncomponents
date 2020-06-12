package com.dncomponents.client.components.table.header.bar;

import com.dncomponents.client.components.table.header.HeaderFiltering;
import com.dncomponents.client.components.table.header.filter.FilterPanel;
import com.dncomponents.client.components.table.header.filter.FilterUtil;
import com.dncomponents.client.views.core.ui.table.headers.bar.panelitems.FilterBarItemView;

/**
 * @author nikolasavic
 */
public class FilterBarItem extends BarItem<HeaderFiltering, FilterBarItemView> {

    public FilterBarItem(HeaderFiltering header, FilterBarPanel filterBarPanel) {
        super(filterBarPanel.getUi().getFilterBarItemView(), header, filterBarPanel);
//        view.setFilterComponent(header.getColumn().getFilterFactory().getHasFilterValue());
        FilterPanel filterValue;
        if (header.getColumn().getFilterPanelFactory() != null)
            filterValue = header.getColumn().getFilterPanelFactory().getFilterPanel();
        else
            filterValue = (FilterPanel) FilterUtil.getFilterValue(header.getColumn(), filterBarPanel.tableUi.getFilterPanelView());
        filterValue.hideClearButton();

        view.setFilterComponent(filterValue);
        view.getFilterComponent().setValue(header.getUserEnteredValue(), header.getComparator());
        view.getFilterComponent().setFilterValueHandler((userEnteredValue, comparator) -> {
            userObject.setUserEnteredValue(userEnteredValue, comparator);
            filterBarPanel.sender = FilterBarItem.this;
            modified();
        });
        view.getOrHandler().setValue(userObject.isOr());
        view.getOrHandler().addValueChangeHandler(event -> {
            userObject.setOr(event.getValue());
            filterBarPanel.sender = FilterBarItem.this;
            modified();
        });
    }

    public void setFirst(boolean first) {
        if (first)
            view.setActionLabel("Where");
        else {
            view.setActionAndOr();
        }
    }

    @Override
    public void setUserObject(HeaderFiltering userObject) {
        super.setUserObject(userObject);
        view.getFilterComponent().setValue(userObject.getUserEnteredValue(), userObject.getComparator());
    }

    @Override
    protected void delete() {
        remove();
        super.delete();
    }

    public void remove() {
        getBarPanel().removeBarItem(this);
        asElement().remove();
    }

    @Override
    protected FilterBarPanel getBarPanel() {
        return (FilterBarPanel) super.getBarPanel();
    }
}