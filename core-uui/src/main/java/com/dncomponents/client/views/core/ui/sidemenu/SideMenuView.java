/*
 * Copyright 2024 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.client.views.core.ui.sidemenu;

import com.dncomponents.client.components.core.CellRenderer;
import com.dncomponents.client.components.core.TreeCellConfig;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.core.pcg.View;

import java.util.List;


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
