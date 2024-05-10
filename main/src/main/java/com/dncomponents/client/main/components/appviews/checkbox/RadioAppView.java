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
import com.dncomponents.client.components.checkbox.Radio;
import com.dncomponents.client.components.checkbox.RadioSelectionGroup;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.stream.Collectors;


public class RadioAppView implements IsElement {
    private static RadioAppView instance;

    @UiField
    HTMLElement root;
    @UiField
    RadioSelectionGroup<Object> eventsRadioGroup;
    @UiField
    TextArea eventsTa;
    @UiField
    RadioSelectionGroup<ItemId> radioGroup;
    @UiField(provided = true)
    Radio<Fruit> rendererRadio;
    @UiField
    public TextArea textArea;
    @UiField
    HTMLElement javaPanel;
    @UiField
    HTMLElement eventsPanel;


    public RadioAppView() {
        initRenderer();
        HtmlBinder.create(RadioAppView.class, this).bind();
        radioGroup.addSelectionHandler((SelectionEvent<Radio<ItemId>> event) ->
                textArea.setValue(event.getSelectedItem().getUserObject().getId()));
        initEvents();
        initJava2();
    }


    private void testingRadio() {
        //good testing for radio!
        RadioSelectionGroup<Fruit> radioSelectionGroup = new RadioSelectionGroup<>();
        radioSelectionGroup.addEntityItems(Fruit.getFruits(4));
        radioSelectionGroup.getItems().forEach(e -> root.appendChild(e.asElement()));

        radioSelectionGroup.getHasValue().addValueChangeHandler(new ValueChangeHandler<Radio<Fruit>>() {
            @Override
            public void onValueChange(ValueChangeEvent<Radio<Fruit>> event) {
                eventsTa.append("value changed " + event.getValue().getValue().toString() + "\n");
            }
        });
        radioSelectionGroup.addSelectionHandler(event -> {
            final Fruit userObject = event.getSelectedItem().getUserObject();
            eventsTa.append("selection changed " + userObject.getName() + " - " + userObject.getDescription() + "\n");
        });
        radioSelectionGroup.getEntitySelectionModel().getHasValue().addValueChangeHandler(new ValueChangeHandler<Fruit>() {
            @Override
            public void onValueChange(ValueChangeEvent<Fruit> event) {
                eventsTa.append("value changed entity: " + event.getValue().toString() + "\n");
            }
        });
    }


    private void initJava() {
        RadioSelectionGroup<Fruit> radioGroup = new RadioSelectionGroup<>();
        radioGroup.addEntityItems(Fruit.getFruits(4));
        radioGroup.getItems().forEach(e -> javaPanel.appendChild(e.asElement()));
    }

    private void initJava2() {
        RadioSelectionGroup<Fruit> radioGroup = new RadioSelectionGroup<>();
        radioGroup.addItems(Fruit.getFruits(4).stream().map(Radio::new).collect(Collectors.toList()));
        radioGroup.getItems().forEach(e -> javaPanel.appendChild(e.asElement()));
    }

    private void initEvents() {
        RadioSelectionGroup<Fruit> radioSelectionGroup = new RadioSelectionGroup<>();
        radioSelectionGroup.addEntityItems(Fruit.getFruits(4));
        radioSelectionGroup.getItems().forEach(e -> eventsPanel.appendChild(e.asElement()));

        radioSelectionGroup.addSelectionHandler(event -> {
            final Fruit userObject = event.getSelectedItem().getUserObject();
            eventsTa.setValue(userObject.getName() + " - " + userObject.getDescription() + "\n");
        });

    }

    private void initRenderer() {
        rendererRadio = new Radio<>();
//        rendererRadio.setRenderer((fruit, slots) ->
//                slots.getMainSlot().innerHTML = "<b>" + fruit.getName() + "</b> " + fruit.getDescription());
        rendererRadio.setUserObject(Fruit.fruits.get(0));
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }


    public static RadioAppView getInstance() {
        if (instance == null) instance = new RadioAppView();
        return instance;
    }
}
