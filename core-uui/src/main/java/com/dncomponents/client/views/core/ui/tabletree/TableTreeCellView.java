package com.dncomponents.client.views.core.ui.tabletree;

import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;

public interface TableTreeCellView extends BaseCellView {
    void addOpenClosedHandler(ClickHandler clickHandler);

    void setOpened(boolean expanded);
}
