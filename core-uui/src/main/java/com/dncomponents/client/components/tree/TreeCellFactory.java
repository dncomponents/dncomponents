package com.dncomponents.client.components.tree;

import com.dncomponents.client.components.Tree;
import com.dncomponents.client.components.core.CellContext;
import com.dncomponents.client.components.core.CellFactory;

public interface TreeCellFactory<T, M> extends CellFactory<TreeNode<T>, M, Tree<T>> {
    @Override
    AbstractTreeCell<T, M> getCell(CellContext<TreeNode<T>, M, Tree<T>> c);
}
