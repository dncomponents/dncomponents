package com.dncomponents.client.views.core.ui.table.headers.bar;

import com.dncomponents.client.components.table.header.bar.ColumnChooseBarPanelView;
import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.FilterBarPanelUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.GroupByBarPanelUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.SortBarPanelUi;

/**
 * @author nikolasavic
 */
public interface TableBarUi extends ComponentUi<TableBarView> {

    GroupByBarPanelUi getGroupByBarPanelUi();

    SortBarPanelUi getSortBarPanelUi();

    FilterBarPanelUi getFilterBarPanelUi();

    ColumnChooseBarPanelView getColumnChooseBarPanelView();
}
