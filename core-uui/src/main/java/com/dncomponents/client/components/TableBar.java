package com.dncomponents.client.components;

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.table.header.HeaderCellHolder;
import com.dncomponents.client.components.table.header.bar.ColumnChooseBarPanel;
import com.dncomponents.client.components.table.header.bar.FilterBarPanel;
import com.dncomponents.client.components.table.header.bar.GroupByBarPanel;
import com.dncomponents.client.components.table.header.bar.SortBarPanel;
import com.dncomponents.client.views.core.ui.table.TableUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.TableBarUi;

/**
 * @author nikolasavic
 */
public class TableBar extends BaseComponent<Object, TableBarUi> {

    public SortBarPanel sortBarPanel;
    public GroupByBarPanel groupByBarPanel;
    public ColumnChooseBarPanel columnChoosePanel;
    public FilterBarPanel filterBarPanel;
    HeaderCellHolder headerCellHolder;

    public TableBar(HeaderCellHolder h, TableBarUi ui, TableUi filterPanelView) {
        super(ui);
        this.headerCellHolder = h;
        columnChoosePanel = new ColumnChooseBarPanel(h.getTable(), getView().getColumnChooseBarPanelView());
        sortBarPanel = new SortBarPanel(headerCellHolder, getView().getSortBarPanelUi());
        groupByBarPanel = new GroupByBarPanel(headerCellHolder, getView().getGroupByBarPanelUi());
        filterBarPanel = new FilterBarPanel(headerCellHolder, getView().getFilterBarPanelUi(), filterPanelView);
        view.getRootView().add(sortBarPanel.getLabel());
        view.getRootView().add(groupByBarPanel.getLabel());
        view.getRootView().add(filterBarPanel.getLabel());
        view.getRootView().add(columnChoosePanel.getLabel());
    }
}