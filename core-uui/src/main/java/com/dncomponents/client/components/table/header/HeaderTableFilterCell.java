package com.dncomponents.client.components.table.header;

import com.dncomponents.client.components.table.header.filter.FilterFactory;
import com.dncomponents.client.components.table.header.filter.FilterUtil;
import com.dncomponents.client.components.table.header.filter.HasFilterValue;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableFilterCellView;

/**
 * @author nikolasavic
 */
public class HeaderTableFilterCell extends HeaderTableSortCell {

    HeaderFiltering headerFiltering;

    HasFilterValue<Object> filterPanel;

    FilterFactory filterFactory = new FilterFactory() {
        @Override
        public HasFilterValue getHasFilterValue() {
            return FilterUtil.getFilterValue(getCellConfig(), getUi().getFilterPanelView());
        }
    };

    public HeaderTableFilterCell() {
    }

    public HeaderTableFilterCell(FilterFactory filterFactory) {
        this.filterFactory = filterFactory;
    }

    public HeaderTableFilterCell(HeaderTableFilterCellView headerCellView) {
        super(headerCellView);
    }

    public HeaderTableFilterCell setFilterPanel(HasFilterValue<Object> filterPanel) {
        this.filterPanel = filterPanel;
        return this;
    }

    @Override
    public HeaderTableFilterCell setText(String columnName) {
        return (HeaderTableFilterCell) super.setText(columnName);
    }

    @Override
    protected void bind() {
        super.bind();
//        if ( filterFactory == null || filterFactory.getHasFilterValue() == null)
//            return;
        if (filterPanel == null)
            filterPanel = filterFactory.getHasFilterValue();

        filterPanel.setFilterValueHandler((userEnteredValue, comparator) -> {
            if (headerFiltering == null)
                headerFiltering = new HeaderFiltering(getCellConfig());
            headerFiltering.setUserEnteredValue(userEnteredValue, comparator);
            headerCellHolder.filtered(headerFiltering);
        });

        getCellView().setFilterPanel(filterPanel);
        headerCellHolder.addFilterHandler(event -> {
                    boolean contains = false;
                    for (HeaderFiltering filtering : event.getModifiers()) {
                        if (filtering.equals(headerFiltering)) {
                            headerFiltering = filtering;
                            contains = true;
                            break;
                        }
                    }
                    if (contains)
                        setFiltered(headerFiltering);
                    else {
                        headerFiltering = null;
                        setFiltered(null);
                    }
                }
        );
    }

    private void setFiltered(HeaderFiltering header) {
        if (header != null) {
            filterPanel.setValue(header.getUserEnteredValue(), header.getComparator());
        } else {
            filterPanel.setValue(null, null);
        }
        getCellView().setFilterIconVisible(header != null);
    }

    //end Filter


    @Override
    public HeaderTableFilterCellView getCellView() {
        return (HeaderTableFilterCellView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getHeaderTableMenuFilterView();
    }

    public void setFilterFactory(FilterFactory filterFactory) {
        this.filterFactory = filterFactory;
    }
}