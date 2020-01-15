package com.dncomponents.client.views.core.ui.tree;

import com.dncomponents.client.dom.handlers.BaseEventListener;

/**
 * @author nikolasavic
 */
public interface ParentTreeCellView extends BaseTreeCellView {
    void setOpened(boolean b);

    void addOpenCloseHandler(BaseEventListener handler);
}
