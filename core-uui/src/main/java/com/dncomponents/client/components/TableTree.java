package com.dncomponents.client.components;

import com.dncomponents.client.components.list.ListTreeMultiSelectionModel;
import com.dncomponents.client.components.table.RowTableCellFactory;
import com.dncomponents.client.components.tree.TreeMultiSelectionModel;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.table.TableUi;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;

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
        ensureRowCellConfig().setCellFactory((RowTableCellFactory) context -> new RowTable());
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
    protected BaseCell createAndInitModelRowCell(TreeNode model) {
        return super.createAndInitModelRowCell(model);
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler<TreeNode<T>> handler) {
        return ensureHandlers().addHandler(CloseEvent.getType(), handler);
    }

    @Override
    public HandlerRegistration addOpenHandler(OpenHandler<TreeNode<T>> handler) {
        return ensureHandlers().addHandler(OpenEvent.getType(), handler);
    }
}