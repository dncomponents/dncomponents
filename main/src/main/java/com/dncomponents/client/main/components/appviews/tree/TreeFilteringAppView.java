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

package com.dncomponents.client.main.components.appviews.tree;

import com.dncomponents.UiField;
import com.dncomponents.client.components.Tree;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.TreeCellConfig;
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class TreeFilteringAppView implements IsElement {

    @UiField
    Tree tree;
    @UiField
    HTMLElement root;
    @UiField
    CheckBox<Object> expandAll;
    @UiField
    TextBox textBox;


    public TreeFilteringAppView() {
        HtmlBinder.create(TreeFilteringAppView.class, this).bind();
        init();
    }

    private void init() {
        initTree();
        tree.expandAll(false);
        expandAll.setValue(true);
        expandAll.addValueChangeHandler(event -> {
            tree.expandAll(event.getValue());
            tree.drawData();
        });
        Filter<TreeNode> filter = new Filter<TreeNode>() {
            @Override
            public boolean test(TreeNode treeNode) {
                if (treeNode == null || treeNode.getUserObject() == null || textBox.getValue() == null)
                    return true;
                String str = treeNode.getUserObject() + "";
                if (str == null)
                    return false;
                return str.contains(textBox.getValue());
            }
        };
        tree.addFilter(filter);
        textBox.addValueChangeHandler(event -> {
            tree.expandAll(true);
            filter.fireFilterChange();
        });
    }

    private void initTree() {
        TreeNode root = new TreeNode("root");
        TreeNode nodeWithChildren = new TreeNode("has children");
        for (int i = 0; i < 200; i++) {
            TreeNode treeNode = new TreeNode("item " + i);
            nodeWithChildren.add(treeNode);
            if (i == 20) {
                for (int j = 0; j < 100; j++) {
                    TreeNode tn = new TreeNode(" **** " + j);
                    treeNode.add(tn);
                    TreeCellConfig<Object, String> cellConfig =
                            new TreeCellConfig<>(treeNode1 -> treeNode1.getUserObject() + "");
                    tree.setCellConfig(tn, cellConfig);
                }
            }
        }
        root.add(nodeWithChildren);
        root.add(new TreeNode("root level 1"));
        root.add(new TreeNode("root level 2"));
        root.add(new TreeNode("root level 3"));
        tree.setRoot(root);
        tree.drawData();
    }

    private static TreeFilteringAppView instance;

    public static TreeFilteringAppView getInstance() {
        if (instance == null)
            instance = new TreeFilteringAppView();
        return instance;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

}
