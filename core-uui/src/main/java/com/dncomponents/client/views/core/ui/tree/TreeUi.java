package com.dncomponents.client.views.core.ui.tree;

import com.dncomponents.client.views.core.pcg.ComponentUi;

/**
 * @author nikolasavic
 */
public interface TreeUi extends ComponentUi<TreeView>,
        HasTreeUi {

    BaseTreeCellView getTreeCellView(String icon);

    ParentTreeCellView getParentTreeCellView(String icon);

    TreeCellCheckboxSimpleView getTreeCellCheckBoxView(String icon);

    TreeCellCheckboxParentView getParentTreeCellCheckboxView(String icon);

    @Override
    default TreeUi getTreeUi() {
        return this;
    }
}

