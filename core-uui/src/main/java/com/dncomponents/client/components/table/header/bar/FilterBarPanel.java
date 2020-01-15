package com.dncomponents.client.components.table.header.bar;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.table.header.HeaderCellHolder;
import com.dncomponents.client.components.table.header.HeaderFiltering;
import com.dncomponents.client.views.core.ui.table.TableUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.FilterBarPanelUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.FilterBarPanelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Shows List of HeaderFiltering items.
 *
 * @author nikolasavic
 */
public class FilterBarPanel extends BaseBarPanel<HeaderFiltering, FilterBarPanelView> {

    private static String DROP_DOWN_TITLE = "Columns to filter";
    FilterBarItem sender;
    List<FilterBarItem> barItems = new ArrayList<>();
    protected TableUi tableUi;

    public FilterBarPanel(HeaderCellHolder cellHolder, FilterBarPanelUi pui, TableUi tableUi) {
        super(pui, cellHolder);
        this.tableUi = tableUi;
        init();
    }

    private void init() {
        view.getRootView().setDropDownTitle(DROP_DOWN_TITLE);
        headerCellHolder.addFilterHandler(event -> {
            view.getRootView().update(event.getModifiers().size());
//            view.getRootView().clear();
            for (HeaderFiltering headerFiltering : event.getModifiers()) {
                boolean contains = false;
                for (FilterBarItem barItem : barItems) {
                    if (barItem.getUserObject().equals(headerFiltering)) {
                        barItem.setUserObject(headerFiltering);
                        contains = true;
                        break;
                    }
                    if (barItem.equals(sender)) {
                        contains = true;
                        break;
                    }
                }
                if (contains)
                    continue;
                addFilterBarItem(headerFiltering);
            }
            checkIfFirstBarItem();
        });
    }

    private void checkIfFirstBarItem() {
        for (int i = 0; i < barItems.size(); i++)
            barItems.get(i).setFirst(i == 0);
    }

    private void addFilterBarItem(HeaderFiltering headerFiltering) {
        FilterBarItem filterBarItem = new FilterBarItem(headerFiltering, this);
        barItems.add(filterBarItem);
        view.getRootView().addBarItem(filterBarItem);
        checkIfFirstBarItem();
    }

    @Override
    public void modify(HeaderFiltering modifier) {
        headerCellHolder.filtered(modifier);
    }

    @Override
    public void addItemToBarPanel(ColumnConfig column) {
        addFilterBarItem(new HeaderFiltering(column));
    }

    protected FilterBarPanelUi getUi() {
        return (FilterBarPanelUi) super.getView();
    }

    public void removeBarItem(FilterBarItem filterBarItem) {
        barItems.remove(filterBarItem);
    }
}