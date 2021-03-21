package com.dncomponents.client.components;

import com.dncomponents.client.components.core.CellContext;
import com.dncomponents.client.components.core.CellFactory;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.close.CloseHandler;
import com.dncomponents.client.components.core.events.close.HasCloseHandlers;
import com.dncomponents.client.components.core.events.open.HasOpenHandlers;
import com.dncomponents.client.components.core.events.open.OpenHandler;
import com.dncomponents.client.components.list.ListTreeMultiSelectionModel;
import com.dncomponents.client.components.table.columnclasses.treetablecell.TableTreeCellParent;
import com.dncomponents.client.components.tree.TreeMultiSelectionModel;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.table.TableUi;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TableTree<T> extends Table<TreeNode<T>> implements HasOpenHandlers<TreeNode<T>>, HasCloseHandlers<TreeNode<T>> {

    public TableTree() {
        super(Ui.get().getTableTreeUi());
    }

    public TableTree(TableUi ui) {
        super(ui);
    }

    Tree.TreeLogic<T> treeLogic = new Tree.TreeLogic(this);

    {
        ensureRowCellConfig().setFieldGetter(t ->
                columnConfigs.stream().map(columnConfig ->
                        columnConfig.getFieldGetter().apply(t)).collect(Collectors.toList()));
//        ensureRowCellConfig().setCellFactory((RowTableCellFactory) context -> new RowTable());
        ensureRowCellConfig().setCellFactory(new CellFactory<TreeNode<T>, List, AbstractCellComponent<TreeNode<T>, ?, ?>>() {
            @Override
            public BaseCell<TreeNode<T>, List> getCell(CellContext<TreeNode<T>, List, AbstractCellComponent<TreeNode<T>, ?, ?>> c) {
                if (c.model.isLeaf())
                    return new RowTable<>();
                else
                    return new TableTreeCellParent();
//                    return AbstractTableTreeCell.getCell(c.model, checkable);

            }
        });
        setSelectionModel(new ListTreeMultiSelectionModel<>(this, view.getRootView()));
    }

    boolean checkable;

    public boolean isCheckable() {
        return checkable;
    }

    public void setCheckable(boolean checkable) {
        this.checkable = checkable;
        this.setSelectionModel(new TreeMultiSelectionModel(this, view.getRootView()));
        getSelectionModel().setNavigator(false);
    }

    @Override
    public ListTreeMultiSelectionModel getSelectionModel() {
        return (ListTreeMultiSelectionModel) super.getSelectionModel();
    }

    public void setRoot(TreeNode<T> root) {
        treeLogic.setRoot(root);
    }

    public TreeNode<T> getRoot() {
        return treeLogic.root;
    }

    public boolean isShowRoot() {
        return treeLogic.isShowRoot();
    }

    public void showRoot(boolean b) {
        treeLogic.setShowRoot(b);
    }

    public boolean isAllCollapsed() {
        return treeLogic.allNodesCollapsed();
    }

    public boolean isAllExpanded() {
        return treeLogic.allNodesExpanded();
    }

    public void expandAll(Boolean b) {
        treeLogic.setExpandAll(b);
    }

    @Override
    protected void filterAndSort() {
        super.filterAndSort();
        rowsFiltered = treeLogic.root.getAllChildNodesInOrderSorted(headerCellHolder.oneComparator(), rowsFiltered);
        rowsFiltered.add(0, treeLogic.root);
        treeLogic.filterAndSort();
    }

    @Override
    public void addFilter(Predicate predicate) {
        super.addFilter(predicate);
    }

    @Override
    protected BaseCell createAndInitModelRowCell(TreeNode model) {
        return super.createAndInitModelRowCell(model);
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler<TreeNode<T>> handler) {
        return addHandler(handler);
    }

    @Override
    public HandlerRegistration addOpenHandler(OpenHandler<TreeNode<T>> handler) {
        return addHandler(handler);
    }
}