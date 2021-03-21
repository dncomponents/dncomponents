package com.dncomponents.client.components.tree;

import com.dncomponents.client.components.core.events.close.CloseEvent;
import com.dncomponents.client.components.core.events.open.OpenEvent;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;

/**
 * Created by nikolasavic
 */
public class TreeCellParent<T, M> extends AbstractTreeCell<T, M> {

    public TreeCellParent() {
    }

    public TreeCellParent(ParentTreeCellView cellView) {
        super(cellView);
    }

    public void setExpanded(boolean b) {
        getModel().setExpanded(b);
        getOwner().drawData();
        if (b)
            OpenEvent.fire(getOwner(), getModel());
        else
            CloseEvent.fire(getOwner(), getModel());
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().addOpenCloseHandler((ClickHandler) mouseEvent
                -> setExpanded(!getModel().isExpanded()));
    }

    @Override
    public void draw() {
        super.draw();
        getCellView().setOpened(getModel().isExpanded());
    }

    @Override
    public ParentTreeCellView getCellView() {
        return (ParentTreeCellView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getParentTreeCellView(icon);
    }

}
