package com.dncomponents.client.components.table.columnclasses.treetablecell;

import com.dncomponents.client.components.core.events.close.CloseEvent;
import com.dncomponents.client.components.core.events.open.OpenEvent;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.table.ParentTableTreeCellView;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;

/**
 * Created by nikolasavic
 */
public class TableTreeCellParent<M> extends AbstractTableTreeCell<M> {

    public TableTreeCellParent() {
    }

    {
        setRenderer(r -> r.valuePanel.innerHTML =
                r.cell.getModel().getUserObject() == null ? "empty" :
                        r.cell.getModel().getUserObject().toString());
    }

    public TableTreeCellParent(ParentTreeCellView cellView) {
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
        getCellView().setSpan(getOwner().getColumnConfigs().size());
        getCellView().setOpened(getModel().isExpanded());
    }

    @Override
    public ParentTableTreeCellView getCellView() {
        return (ParentTableTreeCellView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTreeUi().getParentTreeCellView(null);
    }


}
