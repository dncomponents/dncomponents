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
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.TreeCellConfig;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.List;
import java.util.stream.Collectors;


public class TreeSelectionAppView implements IsElement {


    @UiField
    HTMLElement root;
    @UiField
    Tree tree;
    @UiField
    CheckBox<Object> expandAll;
    @UiField
    TextArea ta;
    @UiField
    Button clearBtn;

    public TreeSelectionAppView() {
        HtmlBinder.create(TreeSelectionAppView.class, this).bind();
        init();
    }

    private void init() {
        tree.expandAll(false);
        expandAll.setValue(true);
//        tree.showRoot(false);
        expandAll.addValueChangeHandler(event -> {
            tree.expandAll(event.getValue());
            tree.drawData();
        });
        clearBtn.addClickHandler(mouseEvent -> ta.setValue(""));
        tree.getSelectionModel().addSelectionHandler((SelectionHandler<List<TreeNode>>) event -> ta.setValue(
                event.getSelectedItem()
                        .stream()
                        .map(e -> e.getUserObject().toString())
                        .collect(Collectors.joining(", "))
        ));

        TreeNode root = new TreeNode("root");
        TreeNode nodeWithChildren = new TreeNode("has children");
        for (int i = 0; i < 200; i++) {
            TreeNode treeNode = new TreeNode("item " + i);
            nodeWithChildren.add(treeNode);
            if (i == 20) {
                for (int j = 0; j < 100; j++) {
                    TreeNode tn = new TreeNode(" **** " + j);
                    treeNode.add(tn);
                    TreeCellConfig<Object, String> cellConfig = new TreeCellConfig<>(objectTreeNode -> objectTreeNode.getUserObject() + "");
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

    private static TreeSelectionAppView instance;

    public static TreeSelectionAppView getInstance() {
        if (instance == null)
            instance = new TreeSelectionAppView();
        return instance;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
