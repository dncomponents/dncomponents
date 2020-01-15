package com.dncomponents.client.views.core.ui.tree;


import com.dncomponents.client.views.core.pcg.ComponentUi;

/**
 * @author nikolasavic
 */
public interface TreeUi extends ComponentUi<TreeView>,
        HasTreeUi {

    BaseTreeCellView getTreeCellView();

    ParentTreeCellView getParentTreeCellView();

    TreeCellCheckboxSimpleView getTreeCellCheckBoxView();

    TreeCellCheckboxParentView getParentTreeCellCheckboxView();

    @Override
    default TreeUi getTreeUi() {
        return this;
    }
}

