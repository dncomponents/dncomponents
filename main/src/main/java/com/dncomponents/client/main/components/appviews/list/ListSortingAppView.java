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
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.Comparator;


public class ListSortingAppView implements IsElement {

    @UiField
    HTMLElement root;

    @UiField
    ListData<Fruit, String> list;
    @UiField
    Button<Boolean> sortAscBtn;
    @UiField
    Button<Boolean> sortDescBtn;
    @UiField
    Button<Boolean> sortOrgBtn;
    @UiField
    CheckBox<String> sortableChb;

    Comparator<Fruit> customComparator = Comparator.comparing(Fruit::getDescription);

    ListSortingAppView() {
        HtmlBinder.create(ListSortingAppView.class, this).bind();
        init();
    }

    private void init() {
        list.getRowCellConfig().setFieldGetter(Fruit::getName);
        list.getRowCellConfig().setFieldSetter(Fruit::setName);
        list.getRowCellConfig().setCellRenderer(r ->
                r.valuePanel.innerHTML = r.cell.getModel().getName() +
                                         " <b>" + r.cell.getModel().getDescription() + "</b>");
        list.setRowsData(Fruit.getFruits(2000));
        list.drawData();
        sortableChb.setValue(list.isSortable(), false);
        sortableChb.addValueChangeHandler((ValueChangeEvent<Boolean> event) -> list.setSortable(event.getValue()));
        sortAscBtn.setUserObject(Boolean.TRUE);
        sortDescBtn.setUserObject(Boolean.FALSE);
        sortOrgBtn.setUserObject(null);
        ClickHandler sortHandler = e -> list.sort((Boolean) BaseComponent.source(e).getUserObject());
        sortAscBtn.addClickHandler(sortHandler);
        sortDescBtn.addClickHandler(sortHandler);
        sortOrgBtn.addClickHandler(sortHandler);
    }


    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static ListSortingAppView instance;

    public static ListSortingAppView getInstance() {
        if (instance == null)
            instance = new ListSortingAppView();
        return instance;
    }
}
