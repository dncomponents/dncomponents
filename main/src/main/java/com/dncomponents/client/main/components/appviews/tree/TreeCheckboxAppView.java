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
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.function.Function;
import java.util.stream.Collectors;


public class TreeCheckboxAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    Tree<Object> tree;
    @UiField
    CheckBox<Object> expandAll;
    @UiField
    TextArea ta = new TextArea();
    @UiField
    Button clearBtn;
    @UiField
    Button showNodeBtn;

    public TreeCheckboxAppView() {
        HtmlBinder.create(TreeCheckboxAppView.class, this).bind();
        init();
    }

    TreeNode treeNodeToShow;

    private void init() {
        tree.setCheckable(true);
        tree.expandAll(false);
        expandAll.setValue(true);
        expandAll.addValueChangeHandler((ValueChangeEvent<Boolean> event) -> {
            tree.expandAll(event.getValue());
            tree.drawData();
        });

        showNodeBtn.addClickHandler(mouseEvent -> tree.showTreeNode(treeNodeToShow));
        clearBtn.addClickHandler(mouseEvent -> ta.setValue(""));

        tree.getSelectionModel().addSelectionHandler(event ->
                ta.setValue(event.getSelectedItem()
                        .stream()
                        .map(e -> e.getUserObject().toString())
                        .collect(Collectors.joining(", "))));


        TreeNode root = new TreeNode("root");
        TreeNode nodeWithChildren = new TreeNode("has children");
        for (int i = 0; i < 200; i++) {
            TreeNode treeNode = new TreeNode("item " + i);
            nodeWithChildren.add(treeNode);
            if (i == 20) {
                for (int j = 0; j < 100; j++) {
                    TreeNode tn = new TreeNode(" **** " + j);
                    if (j == 3) treeNodeToShow = tn;
                    treeNode.add(tn);
                    TreeCellConfig<String, String> treeCellConfig =
                            new TreeCellConfig((Function<TreeNode, Object>) treeNode1 -> treeNode1.getUserObject() + "");
                    treeCellConfig.setCellRenderer(r -> {
                        HTMLElement div = DomUtil.createDiv();
                        div.innerHTML = r.value + "";
                        div.classList.add("fa", "fa-handshake-o");
                        r.valuePanel.appendChild(div);
                    });
                    tree.setCellConfig(tn, treeCellConfig);
                }
            }
        }
        tree.getRowCellConfig().setFieldGetter(objectTreeNode -> objectTreeNode.getUserObject());
        root.add(nodeWithChildren);
        root.add(new TreeNode("root level 1"));
        root.add(new TreeNode("root level 2"));
        root.add(new TreeNode("root level 3"));
        tree.setRoot(root);
        tree.drawData();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static TreeCheckboxAppView instance;

    public static TreeCheckboxAppView getInstance() {
        if (instance == null)
            instance = new TreeCheckboxAppView();
        return instance;
    }

}
