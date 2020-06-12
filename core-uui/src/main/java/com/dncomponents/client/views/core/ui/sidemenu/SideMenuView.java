package com.dncomponents.client.views.core.ui.sidemenu;

import com.dncomponents.client.components.core.CellRenderer;
import com.dncomponents.client.components.core.TreeCellConfig;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.core.pcg.View;

import java.util.List;

/**
 * @author nikolasavic
 */
public interface SideMenuView extends View {
    void showFilter(boolean b);

    void setRoot(TreeNode item);

    void drawData();

    void expandAll(boolean b);

    <T> HandlerRegistration addSelectionHandler(SelectionHandler<List<TreeNode<T>>> handler);

    <T> List<TreeNode<T>> getAll();

    String getValueFromView();

    void registerFilter(Filter<TreeNode> filter);

    void showNode(TreeNode treeNode);

    void setCellConfig(TreeNode menuItem, TreeCellConfig<Object, Object> cellConfig);

    <T> void setRenderer(CellRenderer<TreeNode<T>, Object> renderer);

    void setHeight(String height);

    void setWidth(String width);

    <T> void setSelected(TreeNode<T> item, boolean selected, boolean fireEvent);

    void show(Boolean b);

    boolean isShown();
}
