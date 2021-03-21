package com.dncomponents.client.components.tree;


import com.dncomponents.client.views.core.ui.tree.BaseTreeCellView;

/**
 * Created by nikolasavic
 */
public class TreeCellSimple<T, M> extends AbstractTreeCell<T, M> {

    public TreeCellSimple() {
    }

    public TreeCellSimple(BaseTreeCellView treeCellView) {
        super(treeCellView);
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTreeCellView(icon);
    }

}
