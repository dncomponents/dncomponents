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
import com.dncomponents.client.components.checkbox.RadioSelectionGroup;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class ListBigDataAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement javaTestPanel;
    @UiField
    HTMLElement leftPanel;
    @UiField
    public RadioSelectionGroup<ItemId> radioGroup;
    @UiField
    public ListData<Fruit, String> list;

    public ListBigDataAppView() {
        HtmlBinder.create(ListBigDataAppView.class, this).bind();
        init();
    }

    private void init() {
        list.getRowCellConfig().setFieldGetter(Fruit::getName);
        list.setRowsData(Fruit.getFruits(5));
        list.drawData();
        radioGroup.getEntitySelectionModel().addSelectionHandler((SelectionEvent<ItemId> event) -> {
            int num = Integer.parseInt(event.getSelectedItem().getId());
            list.setRowsData(Fruit.getFruits(num));
            list.drawData();
        });
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static ListBigDataAppView instance;

    public static ListBigDataAppView getInstance() {
        if (instance == null) instance = new ListBigDataAppView();
        return instance;
    }
}
