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
        cellView = getUi().getTreeCellView();
    }

    protected TreeCellSimple(BaseCellBuilder<TreeNode<T>, M, ?> builder) {
        super(builder);
    }

    public static class Builder<T, M> extends AbstractTreeCell.Builder<T, M> {

        @Override
        public TreeCellSimple<T, M> build() {
            return new TreeCellSimple<>(this);
        }
    }

}