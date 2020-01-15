package com.dncomponents.client.views.core.ui.table.headers.bar.panelitems;

import com.dncomponents.client.components.table.header.SortingDirection;
import com.google.gwt.event.logical.shared.SelectionHandler;

/**
 * @author nikolasavic
 */
public interface SortBarItemView extends BarItemView {

    void addSelectionHandler(SelectionHandler<SortingDirection> handler);

    void setDirection(SortingDirection ascending);
}
