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

package com.dncomponents.client.main.components.appviews.autocomplete;

import com.dncomponents.UiField;
import com.dncomponents.client.components.autocomplete.Autocomplete;
import com.dncomponents.client.components.autocomplete.AutocompleteMultiSelect;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.List;


public class AutocompleteListAppView implements IsElement {
    @UiField
    HTMLElement root;
    @UiField
    Autocomplete<Fruit> autocomplete;
    @UiField
    AutocompleteMultiSelect<Fruit> multiSelect;
    @UiField
    Button clearMs;
    @UiField
    Button clearSs;
    @UiField
    Button clearTa;
    @UiField
    TextArea ta;
    @UiField
    Autocomplete<Fruit> acRenderer;
    @UiField
    AutocompleteMultiSelect<Fruit> acMsRenderer;


    public AutocompleteListAppView() {
        HtmlBinder.create(AutocompleteListAppView.class, this).bind();
        init();
        initRenderer();
    }

    private void initRenderer() {
        List<Fruit> fruits = Fruit.getFruits(1000);
        acMsRenderer.getRowCellConfig().setCellRenderer(r -> r.valuePanel.innerHTML = "<b>" + r.value + "</b>");
        acMsRenderer.setRowsData(fruits);
        acRenderer.getRowCellConfig().setCellRenderer(r -> r.valuePanel.innerHTML = "<b>" + r.value + "</b>");
        acRenderer.setRowsData(fruits);
    }

    private void init() {
        List<Fruit> fruits = Fruit.getFruits(1000);
        multiSelect.setRowsData(fruits);
        multiSelect.setHideListOnValueChanged(false);
        multiSelect.addValueChangeHandler(event -> {
            ta.append("multiSelect: value changed event: \n");
            ta.append("***************\n");
            event.getValue().forEach(fruit -> ta.append(fruit.getName() + "\n"));
            ta.append("***************\n");
        });
        multiSelect.addFocusHandler(focusEvent ->
                ta.append("multiSelect: focus event!\n"));
        multiSelect.addBlurHandler(focusEvent ->
                ta.append("multiSelect: blur event!\n"));
        autocomplete.addValueChangeHandler(event ->
                ta.append("autocomplete: value changed event:" + event.getValue().getName() + "\n"));
        autocomplete.addFocusHandler(focusEvent ->
                ta.append("autocomplete: focus event!\n"));
        autocomplete.addBlurHandler(focusEvent ->
                ta.append("autocomplete: blur event!\n"));
        autocomplete.setValue(fruits.get(2));
        autocomplete.setRowsData(fruits);

        clearMs.addClickHandler(mouseEvent -> multiSelect.setValue(null));
        clearSs.addClickHandler(mouseEvent -> autocomplete.setValue(null));
        clearTa.addClickHandler(mouseEvent -> ta.setValue(null));
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static AutocompleteListAppView instance;

    public static AutocompleteListAppView getInstance() {
        if (instance == null) instance = new AutocompleteListAppView();
        return instance;
    }

}
