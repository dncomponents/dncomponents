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
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.list.ListCellCheckbox;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class ListCheckboxAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    public ListData<Fruit, String> list;

    public ListCheckboxAppView() {
        HtmlBinder.create(ListCheckboxAppView.class, this).bind();
        init();
    }

    private void init() {
        list.getRowCellConfig().setFieldGetter(fruit -> fruit.getName());
        list.getRowCellConfig().setCellFactory(c -> new ListCellCheckbox<>());
        list.setRowsData(Fruit.getFruits(2000));
        list.drawData();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static ListCheckboxAppView instance;

    public static ListCheckboxAppView getInstance() {
        if (instance == null) instance = new ListCheckboxAppView();
        return instance;
    }

}
