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
import com.dncomponents.client.components.core.DefaultCellEditor;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.TreeCellConfig;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.main.testing.Person;
import com.dncomponents.client.main.testing.TestingHelper;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class TreeCrudAppView implements IsElement {
    @UiField
    HTMLElement root;
    @UiField
    Tree<Object> tree;

    public TreeCrudAppView() {
        HtmlBinder.create(TreeCrudAppView.class, this).bind();
        testDifferentEntities();
    }


    private void testDifferentEntities() {

        TreeCellConfig<Fruit, String> fruitCellConfig = new TreeCellConfig<>(n -> n.getUserObject().getName());
        fruitCellConfig.setFieldSetter((node, s) -> node.getUserObject().setName(s));
        fruitCellConfig.setClazz(String.class);
        fruitCellConfig.setCellRenderer(r -> {
            r.valuePanel.innerHTML = "" + r.value;
            r.valuePanel.style.color = "blue";
        });
        tree.getRowCellConfig().setCellRenderer(r -> r.valuePanel.innerHTML = r.value + "");
        TreeCellConfig<Person, String> personCellConfig = new TreeCellConfig<>(o -> o.getUserObject().getName());
        personCellConfig.setFieldSetter((n, s) -> n.getUserObject().setName(s));
        personCellConfig.setCellEditorFactory(m -> new DefaultCellEditor<>(new TextArea()));
        personCellConfig.setCellRenderer(r -> r.valuePanel.innerHTML = "" + r.value + " Person");
        TreeNode root = new TreeNode<>("root");
        for (Person Person : TestingHelper.getPeople(22)) {
            TreeNode<Person> tnode = new TreeNode<>(Person);
            root.add(tnode);
            tree.setCellConfig(tnode, personCellConfig);
        }
        for (Fruit fruit : Fruit.getFruits(33)) {
            TreeNode<Fruit> tnode = new TreeNode<>(fruit);
            root.add(tnode);
            tree.setCellConfig(tnode, fruitCellConfig);
        }
        tree.setRoot(root);
        tree.drawData();

    }


    private static TreeCrudAppView instance;

    public static TreeCrudAppView getInstance() {
        if (instance == null)
            instance = new TreeCrudAppView();
        return instance;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
