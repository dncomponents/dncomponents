package com.dncomponents.client.views.core.ui.table.headers.bar.panel;

import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panelitems.SortBarItemView;

/**
 * @author nikolasavic
 */
public interface GroupByBarPanelUi extends ComponentUi<GroupByBarPanelView> {
    SortBarItemView getSortBarItemView();
}
