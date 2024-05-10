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

package com.dncomponents.client.main.components.appviews.checkbox;

import com.dncomponents.UiField;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.checkbox.CheckBoxSelectionGroup;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.List;
import java.util.stream.Collectors;


public class CheckboxAppView implements IsElement {
    private static CheckboxAppView instance;

    @UiField
    HTMLElement root;
    @UiField
    TextArea eventsTa;
    @UiField(provided = true)
    CheckBox<Fruit> rendererCheckbox;
    @UiField
    CheckBox<Fruit> ch3;
    @UiField
    TextArea textArea;
    @UiField
    CheckBoxSelectionGroup<ItemId> group1;
    @UiField
    HTMLElement eventsPanel;
    @UiField
    HTMLElement javaPanel;


    public CheckboxAppView() {
        initRenderer();
        HtmlBinder.create(CheckboxAppView.class, this).bind();
        initJava2();
        selectionGroup();
        initEvents2();
        group1.addSelectionHandler(new SelectionHandler<List<CheckBox<ItemId>>>() {
            @Override
            public void onSelection(SelectionEvent<List<CheckBox<ItemId>>> event) {
                textArea.setValue("");
                for (CheckBox<ItemId> checkBox : event.getSelectedItem()) {
                    textArea.append(checkBox.getUserObject().getId() + "\n");
                }
            }
        });
    }

    private void initJava() {
        CheckBoxSelectionGroup<Fruit> checkBoxGroup = new CheckBoxSelectionGroup<>();
        checkBoxGroup.addEntityItems(Fruit.getFruits(4));
        checkBoxGroup.getItems().forEach(e -> javaPanel.appendChild(e.asElement()));
    }

    private void initJava2() {
        CheckBoxSelectionGroup<Fruit> checkBoxGroup = new CheckBoxSelectionGroup<>();
        checkBoxGroup.addItems(Fruit.getFruits(4).stream().map(CheckBox::new).collect(Collectors.toList()));
        checkBoxGroup.getItems().forEach(e -> javaPanel.appendChild(e.asElement()));
    }

    private void initRenderer() {
        rendererCheckbox = new CheckBox<>();
        rendererCheckbox.setRenderer((fruit, slots) -> slots.getMainSlot().innerHTML = "<b>" + fruit.getName() + "</b>");
        rendererCheckbox.setUserObject(Fruit.fruits.get(0));
    }

    private void initEvents() {
        CheckBoxSelectionGroup<Fruit> eventsCheckboxGroup = new CheckBoxSelectionGroup<>();
        for (Fruit fruit : Fruit.getFruits(4)) {
            CheckBox<Fruit> checkBox = new CheckBox<>(fruit);
            eventsCheckboxGroup.addItem(checkBox);
            eventsPanel.appendChild(checkBox.asElement());
        }
        eventsCheckboxGroup.addSelectionHandler(new SelectionHandler<List<CheckBox<Fruit>>>() {
            @Override
            public void onSelection(SelectionEvent<List<CheckBox<Fruit>>> event) {
                eventsTa.setValue("");
                for (CheckBox<Fruit> checkBox : event.getSelectedItem()) {
                    eventsTa.append(checkBox.getUserObject().getName() + " - " + checkBox.getUserObject().getDescription() + "\n");
                }
            }
        });
    }

    private void initEvents2() {
        CheckBoxSelectionGroup<Fruit> eventsCheckboxGroup = new CheckBoxSelectionGroup<>();
        eventsCheckboxGroup.addEntityItems(Fruit.getFruits(4));
        eventsCheckboxGroup.getItems().forEach(e -> eventsPanel.appendChild(e.asElement()));

        eventsCheckboxGroup.addSelectionHandler(event -> {
            eventsTa.setValue("");
            for (CheckBox<Fruit> checkBox : event.getSelectedItem()) {
                eventsTa.append(checkBox.getUserObject().getName() + " - " + checkBox.getUserObject().getDescription() + "\n");
            }
        });
    }


    private void selectionGroup() {
        CheckBoxSelectionGroup<Fruit> group = new CheckBoxSelectionGroup<>();
        CheckBox asc = new CheckBox(Fruit.fruits.get(0));
        asc.setIndeterminate(true);
        CheckBox desc = new CheckBox(Fruit.fruits.get(1));
        group.addItem(asc);
        group.addItem(desc);
        asc.setValue(true, true);
        Button selectionBtn = new Button("Selection");
        group.getEntitySelectionModel().addSelectionHandler(new SelectionHandler<List<Fruit>>() {
            @Override
            public void onSelection(SelectionEvent<List<Fruit>> event) {
                selectionBtn.setText(event.getSelectedItem() + "");
            }
        });
    }


    @Override
    public HTMLElement asElement() {
        return root;
    }

    public static CheckboxAppView getInstance() {
        if (instance == null) instance = new CheckboxAppView();
        return instance;
    }
}
