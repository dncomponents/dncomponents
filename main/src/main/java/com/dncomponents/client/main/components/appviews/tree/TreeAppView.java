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
import com.dncomponents.client.components.core.CellRenderer;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.RendererContext;
import com.dncomponents.client.components.core.TreeCellConfig;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.dom.handlers.DoubleClickHandler;
import com.dncomponents.client.dom.handlers.MouseOverHandler;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;

import java.util.stream.Collectors;


public class TreeAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    Tree<Object> tree;
    @UiField
    CheckBox<Object> showRoot;
    @UiField
    CheckBox<Object> expandAll;
    //renderer
    @UiField
    Tree<Object> treeRenderer;
    //events
    @UiField
    Tree<Object> eventsTree;
    @UiField
    TextArea eventsTa;


    public TreeAppView() {
        HtmlBinder.create(TreeAppView.class, this).bind();
        init();
        initRenderer();
        initEvents();
        tree.getRowCellConfig()
                .setCellFactory(c -> c.createDefaultCell().setRenderer(r -> r.valuePanel.innerHTML = r.value + ""));
    }

    private void initEvents() {
        TreeNode root = new TreeNode("root");
        TreeNode node1 = new TreeNode("node 1");
        TreeNode node2 = new TreeNode("node 2");
        TreeNode node21 = new TreeNode("node 21");
        TreeNode node22 = new TreeNode("node 22");
        TreeNode node23 = new TreeNode("node 23");
        node2.add(node21);
        node2.add(node22);
        node2.add(node23);
        root.add(node1);
        root.add(node2);
        eventsTree.setRoot(root);
        eventsTree.addOpenHandler(event ->
                eventsTa.append("open: " + event.getOwner().getUserObject() + "\n"));
        eventsTree.addCloseHandler(event ->
                eventsTa.append("close: " + event.getOwner().getUserObject() + "\n"));
        eventsTree.addCellHandler((DoubleClickHandler) mouseEvent ->
                eventsTa.append("double clicked: " + eventsTree.getCell(mouseEvent).getModel().getUserObject() + "\n"));
        eventsTree.addCellHandler((MouseOverHandler) mouseEvent ->
                eventsTa.append("mouse over: " + eventsTree.getCell(mouseEvent).getModel().getUserObject() + "\n"));
        eventsTree.drawData();
    }

    private void initRenderer() {
        TreeNode root = new TreeNode("root");
        TreeNode node1 = new TreeNode("node 1");
        TreeNode<Fruit> node2 = new TreeNode(new Fruit("Watermelon", "very sweet"));
        TreeCellConfig<Fruit, String> config =
                new TreeCellConfig<>(fruitTreeNode -> fruitTreeNode.getUserObject().getName());
        config.setCellRenderer(r -> {
            r.valuePanel.style.color = "blue";
            r.valuePanel.innerHTML = r.value;
        });
//        treeRenderer.setCellConfig(node2, config);
        treeRenderer.getRowCellConfig().setCellRenderer(new CellRenderer<TreeNode<Object>, Object>() {
            @Override
            public void setValue(RendererContext<TreeNode<Object>, Object> r) {
                r.valuePanel.innerHTML = "<b>" + r.value + "</b>";
            }
        });
//        treeRenderer.getRowCellConfig().setCellFactory(new TreeCellFactory<Object, Object>() {
//            @Override
//            public AbstractTreeCell<Object, Object> getCell(CellContext<TreeNode<Object>, Object, Tree<Object>> c) {
//                return AbstractTreeCell.getCell(c.model, c.owner.isCheckable()).setRenderer(new CellRenderer<TreeNode<Object>, Object>() {
//                    @Override
//                    public void setValue(RendererContext<TreeNode<Object>, Object> r) {
//                        r.valuePanel.innerHTML = "asdf";
//                    }
//                });
//            }
//        });
        root.add(node1);
        root.add(node2);
        treeRenderer.setRoot(root);
        treeRenderer.drawData();

    }


    private void init() {
        tree.expandAll(false);
        tree.getRowCellConfig().setFieldGetter(TreeNode::getUserObject);

        showRoot.setValue(tree.isShowRoot());
        expandAll.setValue(tree.isAllExpanded());

        tree.addDropHandler(event -> {
            DomGlobal.alert(event.getItem().getUserObject());
            DomGlobal.alert("parent: " + event.getItem().getParent().getUserObject());
            DomGlobal.alert("siblings: " +
                            event.getItem()
                                    .getSiblings()
                                    .stream().map(node -> node.getUserObject() + "")
                                    .collect(Collectors.joining(", ")));
        });
        tree.setDragAndDropEnabled(true);

        TreeNode root = new TreeNode("root");
        TreeNode node1 = new TreeNode("node 1");
        TreeNode node2 = new TreeNode("node 2");
        TreeNode node3 = new TreeNode("node 3");
        root.add(node1);
        root.add(node2);
        root.add(node3);

        TreeNode node11 = new TreeNode("node 11");
        node1.add(node11);
        TreeNode node12 = new TreeNode("node 12");
        node1.add(node12);

        expandAll.addValueChangeHandler((ValueChangeEvent<Boolean> event) -> {
            tree.expandAll(event.getValue());
            tree.drawData();
        });

        showRoot.addValueChangeHandler(event -> {
            tree.showRoot(event.getValue());
            tree.drawData();
        });

        tree.setRoot(root);
        tree.drawData();
    }


    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static TreeAppView instance;

    public static TreeAppView getInstance() {
        if (instance == null)
            instance = new TreeAppView();
        return instance;
    }
}
