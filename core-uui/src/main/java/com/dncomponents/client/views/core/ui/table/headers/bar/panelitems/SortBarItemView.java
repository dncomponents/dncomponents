package com.dncomponents.client.views.core.ui.table.headers.bar.panelitems;

import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.table.header.SortingDirection;

/**
 * @author nikolasavic
 */
public interface SortBarItemView extends BarItemView {

    void addSelectionHandler(SelectionHandler<SortingDirection> handler);

    void setDirection(SortingDirection ascending);
}
