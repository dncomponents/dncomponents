package com.dncomponents.client.components.core;

import com.dncomponents.client.components.tree.TreeCellFactory;
import com.dncomponents.client.components.tree.AbstractTreeCell;
import com.dncomponents.client.components.tree.TreeNode;

import java.util.function.BiConsumer;
import java.util.function.Function;
/**
 * @author nikolasavic
 */
public class TreeCellConfig<T, M> extends CellConfig<TreeNode<T>, M> {

    protected CellRenderer<TreeNode, T> renderer;

    {
        setCellFactory(c -> c.createDefaultCell().initWithBuilder(builder));
        builder = new AbstractTreeCell.Builder() {
            @Override
            public AbstractTreeCell build() {
                return null;
            }
        };
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

    public TreeCellConfig<T, M> setCellBuilder(BuilderSet<T, M> b) {
        b.setBuilder(getCellBuilder());
        return this;
    }

    public interface BuilderSet<T, M> {
        void setBuilder(AbstractTreeCell.Builder<T, M> b);
    }

    @Override
    public AbstractTreeCell.Builder<T, M> getCellBuilder() {
        return (AbstractTreeCell.Builder<T, M>) super.getCellBuilder();
    }

}