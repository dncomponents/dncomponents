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

package com.dncomponents.client.main.components.appviews.list;

import com.dncomponents.UiField;
import com.dncomponents.client.components.ListData;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class ListCrudAppView implements IsElement {
    @UiField
    HTMLElement root;
    @UiField
    public Button addItemButton;
    @UiField
    public Button removeSelectionButton;
    @UiField
    public ListData<Fruit, String> list;
    @UiField
    public Button startEditingButton;
    @UiField
    public CheckBox editableListCheckBox;

    public ListCrudAppView() {
        HtmlBinder.create(ListCrudAppView.class, this).bind();
        init();
    }

    private static int i;

    private void init() {
        list.getRowCellConfig().setFieldGetter(Fruit::getName);
        list.getRowCellConfig().setFieldSetter(Fruit::setName);
        list.getRowCellConfig().setClazz(String.class);
        list.setRowsData(Fruit.getFruits(2000));
        list.drawData();

        //add item
        addItemButton.addClickHandler(mouseEvent -> {
                    list.insertRow(new Fruit("Fruit " + i++, "description " + i), 0);
                    list.drawData();
                }
        );
        //start editing
        startEditingButton.addClickHandler(mouseEvent ->
                list.getRowCell(list
                                .getSelectionModel()
                                .getFirstSelected())
                        .startEditing());
        editableListCheckBox.setValue(list.isEditable());
        editableListCheckBox.addValueChangeHandler((ValueChangeEvent event) -> list.setEditable((Boolean) event.getValue()));
        //remove
        list.getSelectionModel().addSelectionHandler(event ->
                removeSelectionButton.setEnabled(event.getSelectedItem().size() > 0));
        removeSelectionButton.setEnabled(!list.getSelectionModel().getSelection().isEmpty());
        removeSelectionButton.addClickHandler(mouseEvent ->
                list.removeRows(list.getSelectionModel().getSelection()));

    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static ListCrudAppView instance;

    public static ListCrudAppView getInstance() {
        if (instance == null) instance = new ListCrudAppView();
        return instance;
    }
}
