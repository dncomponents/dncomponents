package com.dncomponents.client.views.core.ui.table.headers.bar.panel;

import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panelitems.FilterBarItemView;

/**
 * @author nikolasavic
 */
public interface FilterBarPanelUi extends ComponentUi<FilterBarPanelView> {
    FilterBarItemView getFilterBarItemView();
}
