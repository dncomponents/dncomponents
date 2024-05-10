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
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class ListFilterAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    public TextBox textBox;
    @UiField
    public ListData<Fruit, String> list;

    ListFilterAppView() {
        HtmlBinder.create(ListFilterAppView.class, this).bind();
        init();
    }

    private void init() {
        list.getRowCellConfig().setFieldGetter(fruit -> fruit.getName());
        list.setRowsData(Fruit.getFruits(2000));
        list.drawData();
        Filter<Fruit> filter = new Filter<Fruit>() {
            @Override
            public boolean test(Fruit fruit) {
                try {
                    String val = textBox.getValueOrThrow();
                    if (val == null)
                        return true;
                    return fruit.getName().toLowerCase().contains(val.toLowerCase());
                } catch (ValidationException e) {
                    return false;
                }
            }
        };
        textBox.addHandler((KeyUpHandler) event -> filter.fireFilterChange());
        list.addFilter(filter);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static ListFilterAppView instance;

    public static ListFilterAppView getInstance() {
        if (instance == null) instance = new ListFilterAppView();
        return instance;
    }
}
