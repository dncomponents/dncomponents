package com.dncomponents.client.components.core;

import com.dncomponents.client.components.tree.TreeCellFactory;
import com.dncomponents.client.components.tree.TreeNode;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author nikolasavic
 */
public class TreeCellConfig<T, M> extends CellConfig<TreeNode<T>, M> {

    protected CellRenderer<TreeNode, T> renderer;

    {
        setCellFactory(c -> c.createDefaultCell());
    }

    public TreeCellConfig() {
        super(tTreeNode -> ((M) tTreeNode.getUserObject()));
    }

    public TreeCellConfig(Function<TreeNode<T>, M> fieldGetter) {
        super(fieldGetter);
    }

    public TreeCellConfig(Function<TreeNode<T>, M> fieldGetter, BiConsumer<TreeNode<T>, M> fieldSetter) {
        super(fieldGetter, fieldSetter);
    }

    public TreeCellConfig<T, M> setCellFactory(TreeCellFactory<T, M> cellFactory) {
        super.setCellFactory(cellFactory);
        return this;
    }

}
