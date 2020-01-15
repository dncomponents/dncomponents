package com.dncomponents.client.components;

import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.core.ui.tree.TreeUi;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.logical.shared.HasOpenHandlers;

import java.util.List;

public abstract class AbstractTree<M> extends AbstractCellComponent<TreeNode<M>, Object, TreeUi> implements HasOpenHandlers<TreeNode<M>>, HasCloseHandlers<TreeNode<M>> {
    public AbstractTree(TreeUi ui) {
        super(ui);
    }

    public AbstractTree(TreeUi ui, List<TreeNode<M>> rows) {
        super(ui, rows);
    }
}
