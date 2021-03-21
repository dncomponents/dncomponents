package com.dncomponents.client.views.core.ui.table.headers;

import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.pcg.cell.CellView;

/**
 * @author nikolasavic
 */
public interface HeaderTableEditCellView extends CellView {
    void addAddHandler(ClickHandler clickHandler);
}
