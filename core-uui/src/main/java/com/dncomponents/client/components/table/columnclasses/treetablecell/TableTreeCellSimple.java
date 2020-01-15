package com.dncomponents.client.components.table.columnclasses.treetablecell;

import com.dncomponents.client.views.core.ui.tree.BaseTreeCellView;

/**
 * Created by nikolasavic
 */
public class TableTreeCellSimple<M> extends AbstractTableTreeCell<M> {

    public TableTreeCellSimple() {
    }

    public TableTreeCellSimple(BaseTreeCellView treeCellView) {
        super(treeCellView);
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTreeUi().getTreeCellView();
    }

}
