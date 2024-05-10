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
import com.dncomponents.client.components.autocomplete.AutocompleteTree;
import com.dncomponents.client.components.autocomplete.AutocompleteTreeMultiSelect;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class AutocompleteTreeAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    AutocompleteTree<Object> autocompleteTree;
    @UiField
    AutocompleteTreeMultiSelect<Object> autocompleteTreeMultiSelect;
    @UiField
    Button clearMs;
    @UiField
    Button clearSs;
    @UiField
    Button clearTa;
    @UiField
    TextArea ta;
    //renderer
    @UiField
    AutocompleteTree acTreeRenderer;
    @UiField
    AutocompleteTreeMultiSelect acMsTreeRenderer;


    public AutocompleteTreeAppView() {
        HtmlBinder.create(AutocompleteTreeAppView.class, this).bind();
        init();
        initRenderer();
    }

    private void initRenderer() {
        acMsTreeRenderer.getRowCellConfig().setCellRenderer(r -> r.valuePanel.innerHTML = "<b>" + r.value + "</b>");
        acMsTreeRenderer.setRoot(Fruit.getFruitsTree());
        acTreeRenderer.getRowCellConfig().setCellRenderer(r -> r.valuePanel.innerHTML = "<b>" + r.value + "</b>");
        acTreeRenderer.setRoot(Fruit.getFruitsTree());
    }

    private void init() {
        autocompleteTree.setRoot(Fruit.getFruitsTree());
        autocompleteTreeMultiSelect.setRoot(Fruit.getFruitsTree());
        autocompleteTreeMultiSelect.addValueChangeHandler(event -> {
            ta.append("multiSelect: value changed event: \n");
            ta.append("***************\n");
            event.getValue().forEach(node -> {
                ta.append(node.getUserObject() + "\n");
            });
            ta.append("***************\n");
        });
        autocompleteTreeMultiSelect.addFocusHandler(focusEvent ->
                ta.append("multiSelect: focus event!\n"));
        autocompleteTreeMultiSelect.addBlurHandler(focusEvent ->
                ta.append("multiSelect: blur event!\n"));
        autocompleteTree.addValueChangeHandler(event ->
                ta.append("autocomplete: value changed event:" + ((Fruit) event.getValue().getUserObject()).getName() + "\n"));
        autocompleteTree.addFocusHandler(focusEvent ->
                ta.append("autocomplete: focus event!\n"));
        autocompleteTree.addBlurHandler(focusEvent ->
                ta.append("autocomplete: blur event!\n"));

        clearMs.addClickHandler(mouseEvent -> autocompleteTreeMultiSelect.setValue(null));
        clearSs.addClickHandler(mouseEvent -> autocompleteTree.setValue(null));
        clearTa.addClickHandler(mouseEvent -> ta.setValue(null));
    }


    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static AutocompleteTreeAppView instance;

    public static AutocompleteTreeAppView getInstance() {
        if (instance == null) instance = new AutocompleteTreeAppView();
        return instance;
    }

}
