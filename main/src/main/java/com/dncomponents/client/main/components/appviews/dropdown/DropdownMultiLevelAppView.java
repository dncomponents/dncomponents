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

package com.dncomponents.client.main.components.appviews.dropdown;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.multi.DropDownMultiLevel;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import static com.dncomponents.client.main.testing.Fruit.getFruitsTree;


public class DropdownMultiLevelAppView implements IsElement {
    @UiField
    public HTMLElement root;
    @UiField
    public TextArea textArea;
    @UiField
    public HTMLElement javaPanel;
    @UiField
    public DropDownMultiLevel<Object> dpEvents;
    @UiField
    public TextArea ta;
    @UiField
    public DropDownMultiLevel<Object> dpRenderer;

    {
        HtmlBinder.create(DropdownMultiLevelAppView.class, this).bind();
    }

    @UiField
    public DropDownMultiLevel<ItemId> dropDownMultiLevel;

    public DropdownMultiLevelAppView() {
        init();
        initEvents();
        initRenderer();
//        java();
    }

    private void initRenderer() {
        dpRenderer.setItemRenderer((objectTreeNode, slots) ->
                slots.getMainSlot().innerHTML = "<b>" + objectTreeNode.getUserObject() + "</b>");
        dpRenderer.setRoot(getFruitsTree());
    }

    private void initEvents() {
        dpEvents.setRoot(getFruitsTree());
        dpEvents.getSingleSelectionModel().addSelectionHandler(event ->
                ta.append("Item selection event: " + event.getSelectedItem()));
        dpEvents.addOpenHandler(event -> ta.append("Open event\n"));
        dpEvents.addCloseHandler(event -> ta.append("Close event\n"));
    }

    private void java() {
        DropDownMultiLevel<ItemId> dropDownMultiLevel = new DropDownMultiLevel<>();
        dropDownMultiLevel.setButtonContent("Java multi level");
        TreeNode<ItemId> root = new TreeNode<>(new ItemId("root", "root"));
        root.add(new TreeNode<>(new ItemId("one", "item 1")));
        root.add(new TreeNode<>(new ItemId("two", "item 2")));
        TreeNode<ItemId> moreItem = new TreeNode<>(new ItemId("more", "show more"));
        root.add(moreItem);
        moreItem.add(new TreeNode<>(new ItemId("one_one", "item 11")));
        moreItem.add(new TreeNode<>(new ItemId("two_two", "item 22")));
        moreItem.add(new TreeNode<>(new ItemId("three_three", "item 33")));
        dropDownMultiLevel.setRoot(root);
        this.javaPanel.appendChild(dropDownMultiLevel.asElement());
    }

    private void init() {
        //init code here
        dropDownMultiLevel.getSingleSelectionModel().addSelectionHandler(event ->
                textArea.setValue(event.getSelectedItem().getUserObject().getId())
        );
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static DropdownMultiLevelAppView instance;

    public static DropdownMultiLevelAppView getInstance() {
        if (instance == null)
            instance = new DropdownMultiLevelAppView();
        return instance;
    }


}
