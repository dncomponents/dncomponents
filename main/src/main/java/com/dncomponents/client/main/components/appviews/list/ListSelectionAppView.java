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
import com.dncomponents.client.components.checkbox.Radio;
import com.dncomponents.client.components.checkbox.RadioSelectionGroup;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class ListSelectionAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    ListData<Fruit, String> list;
    @UiField
    public Radio<DefaultMultiSelectionModel.SelectionMode> singleRadio;
    @UiField
    public Radio<DefaultMultiSelectionModel.SelectionMode> multiRadio;
    @UiField
    public RadioSelectionGroup<DefaultMultiSelectionModel.SelectionMode> selectionRadioGroup;
    @UiField
    public Button selectButton;

    ListSelectionAppView() {
        HtmlBinder.create(ListSelectionAppView.class, this).bind();
        init();
    }


    private void init() {
        singleRadio.setUserObject(DefaultMultiSelectionModel.SelectionMode.SINGLE);
        singleRadio.setLabel("Single selection");
        multiRadio.setUserObject(DefaultMultiSelectionModel.SelectionMode.MULTI);
        multiRadio.setLabel("Multi selection (cmd + left click)");

        list.getRowCellConfig().setFieldGetter(Fruit::getName);
        list.getRowCellConfig().setFieldSetter(Fruit::setName);
        list.setRowsData(Fruit.getFruits(2000));
        list.drawData();

        selectButton.addClickHandler(e -> list.getSelectionModel().setSelected(list.getRowsData().get(3), true, true));
        selectionRadioGroup.getEntitySelectionModel().addSelectionHandler((SelectionEvent<DefaultMultiSelectionModel.SelectionMode> event) ->
                list.getSelectionModel().setSelectionMode(event.getSelectedItem()));
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static ListSelectionAppView instance;

    public static ListSelectionAppView getInstance() {
        if (instance == null)
            instance = new ListSelectionAppView();
        return instance;
    }
}
