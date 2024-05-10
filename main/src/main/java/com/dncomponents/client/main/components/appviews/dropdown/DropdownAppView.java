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
import com.dncomponents.bootstrap.client.button.FontAwesome;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.dropdown.DropDown;
import com.dncomponents.client.components.dropdown.DropDownItem;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;


public class DropdownAppView implements IsElement {
    @UiField
    HTMLElement root;
    @UiField
    HTMLElement javaTestPanel;
    @UiField
    HTMLElement rendererPanel;
    @UiField
    TextArea ta;
    @UiField
    DropDown<Fruit> dpEvents;
    @UiField
    public TextArea textArea;

    @UiField
    public DropDown<ItemId> dropDown;

    {
        HtmlBinder.create(DropdownAppView.class, this).bind();
    }

    public DropdownAppView() {
        initDropdown();
        initRenderer();
        initEvents();
        dropDown.addSelectionHandler(event ->
                textArea.setValue(event.getSelectedItem()
                        .getUserObject().getId()));
    }

    private void initEvents() {
        for (Fruit fruit : Fruit.fruits)
            dpEvents.addItem(new DropDownItem<>(dpEvents, fruit));
        dpEvents.setButtonContent("Fruits");
        dpEvents.addSelectionHandler(event ->
                ta.append("Item selection event: " + event.getSelectedItem().getUserObject() + "\n"));
        dpEvents.getEntitySelectionModel().addSelectionHandler(event -> {
            DomGlobal.window.alert("entitiy: " + event.getSelectedItem().getName());
        });
        dpEvents.addSelectionHandler(event -> ta.append("Selection Event:" + event.getSelectedItem().toString() + "\n"));
        dpEvents.addOpenHandler(event -> ta.append("Open event\n"));
        dpEvents.addCloseHandler(event -> ta.append("Close event\n"));
    }

    private void initRenderer() {
        DropDown<Fruit> dropDown = new DropDown<>();
        dropDown.setItemRenderer((userObject, slots) -> slots.getMainSlot()
                .appendChild(FontAwesome.CIRCLE_THIN.getIcon())
                .textContent = "  " + userObject.getName());
        for (Fruit fruit : Fruit.fruits) {
            DropDownItem<Fruit> fruitDropDownItem = new DropDownItem<>(dropDown, fruit);
            dropDown.addItem(fruitDropDownItem);
        }
        dropDown.setButtonContent("Fruits");
        rendererPanel.appendChild(dropDown.asElement());
    }

    private void initDropdown() {
        DropDown dropDown = new DropDown();
        dropDown.setButtonContent("Dropdown");
        dropDown.addItem(new DropDownItem(dropDown, "item 1"));
        dropDown.addItem(new DropDownItem(dropDown, "item 2"));
        dropDown.addItem(new DropDownItem(dropDown, "item 3"));
        javaTestPanel.appendChild(dropDown.asElement());
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static DropdownAppView instance;

    public static DropdownAppView getInstance() {
        if (instance == null) instance = new DropdownAppView();
        return instance;
    }

}
