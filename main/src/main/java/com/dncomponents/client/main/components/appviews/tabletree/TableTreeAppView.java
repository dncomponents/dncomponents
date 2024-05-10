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

package com.dncomponents.client.main.components.appviews.tabletree;

import com.dncomponents.UiField;
import com.dncomponents.client.components.TableTree;
import com.dncomponents.client.components.TreeTableColumnConfig;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.main.testing.Person;
import com.dncomponents.client.main.testing.TestingHelper;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.ArrayList;

public class TableTreeAppView implements IsElement {
    private static TableTreeAppView instance;
    @UiField
    public HTMLElement root;
    @UiField
    public CheckBox expandAll;
    @UiField
    public TextBox textBox;
    @UiField
    public CheckBox showRoot;

    TableTree<Person> tableTree = new TableTree<>();

    {
        HtmlBinder.create(TableTreeAppView.class, this).bind();
    }

    public TableTreeAppView() {
        init();
    }

    private void init() {
        tableTree.setMultiSorting(true);
        tableTree.setCheckable(true);
        expandAll.setLabel("Expand all");
        expandAll.setValue(tableTree.isAllExpanded());
        expandAll.addValueChangeHandler(event -> {
            tableTree.expandAll((Boolean) event.getValue());
            tableTree.drawData();
        });
        //show root
        showRoot.setLabel("Show root");
        showRoot.setValue(tableTree.isShowRoot());
        showRoot.addValueChangeHandler(e -> {
            tableTree.showRoot((Boolean) e.getValue());
            tableTree.drawData();
        });

        //filter
        tableTree.addFilter(node ->
                ((TreeNode) node).getUserObject()
                        .toString()
                        .toLowerCase()
                        .contains(textBox.getValue() == null ? "" : textBox.getValue().toLowerCase()));

        textBox.addHandler((KeyUpHandler) event -> tableTree.drawData());

        tableTree.addOpenHandler(event -> expandAll.setValue(tableTree.isAllExpanded()));
        tableTree.addCloseHandler(event -> expandAll.setValue(tableTree.isAllExpanded()));

        TreeTableColumnConfig<Person, String> nameColumn2 =
                new TreeTableColumnConfig<>(Person::getName, Person::setName);
        tableTree.setEditable(true);
        nameColumn2.setName("Name");
        nameColumn2.setEditable(true);

        TreeTableColumnConfig<Person, Boolean> active =
                new TreeTableColumnConfig<>(Person::isActive, Person::setActive);
        active.setEditable(true);
        active.setClazz(Boolean.class);
        active.setName("Active");

        TreeTableColumnConfig<Person, Integer> ageColumn =
                new TreeTableColumnConfig<>(Person::getAge, Person::setAge);
        ageColumn.setName("Age");
        ageColumn.setEditable(true);
        TreeTableColumnConfig<Person, String> colorColumn =
                new TreeTableColumnConfig<>(Person::getCurrentColor);
        colorColumn.setName("Color");
        tableTree.addColumn(nameColumn2, active, ageColumn, colorColumn);
        TreeNode rootNode = new TreeNode("People");
        TreeNode group1 = new TreeNode("Group one");
        TestingHelper.getPeople(5);
        TreeNode people1 = new TreeNode(TestingHelper.getPeople(1).get(0));
        people1.add(group1);
        TreeNode group2 = new TreeNode("Group two");
        TreeNode group3 = new TreeNode("Group three");
        rootNode.add(people1);
        rootNode.add(group2);
        rootNode.add(group3);

        ArrayList<Person> people = TestingHelper.getPeople(200);
        for (Person person : people) {
            group1.add(new TreeNode(person));
            group2.add(new TreeNode(person));
            group3.add(new TreeNode(person));
        }
        tableTree.setRoot(rootNode);
        root.appendChild(tableTree.asElement());
        tableTree.drawData();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    public static TableTreeAppView getInstance() {
        if (instance == null)
            instance = new TableTreeAppView();
        return instance;
    }
}
