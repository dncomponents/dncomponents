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
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.dom.handlers.DoubleClickHandler;
import com.dncomponents.client.dom.handlers.MouseOutHandler;
import com.dncomponents.client.dom.handlers.MouseOverHandler;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class ListAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement examplePanel;
    @UiField
    ListData<Fruit, String> listData;
    @UiField
    ListData<Fruit, String> eventsList;
    @UiField
    TextArea eventsTa;
    @UiField
    ListData<ItemId, String> list;
    @UiField
    TextArea ta;

    public ListAppView() {
        HtmlBinder.create(ListAppView.class, this).bind();
        init();
        initRenderer();
        initEvents();
    }

    private void initEvents() {
        eventsList.getRowCellConfig().setFieldGetter(Fruit::getName);
        eventsList.setRowsData(Fruit.getFruits(2000));
        eventsList.drawData();

        eventsList.addCellHandler((MouseOverHandler) mouseEvent ->
                eventsTa.append("mouse over event: " + eventsList.getCell(mouseEvent).getModel().getName() + "\n"));
        eventsList.addCellHandler((DoubleClickHandler) mouseEvent ->
                eventsTa.append("double click event: " + eventsList.getCell(mouseEvent).getModel().getName() + "\n"));
        eventsList.addCellHandler((MouseOutHandler) mouseEvent ->
                eventsTa.append("mouse out event: " + eventsList.getCell(mouseEvent).getModel().getName() + "\n"));
    }

    private void init() {
        list.getSelectionModel().addSelectionHandler(event -> {
            for (ItemId idItem : event.getSelectedItem()) {
                ta.setValue("");
                ta.append(idItem.getId() + "\n");
            }
        });
    }

    private void initRenderer() {
        listData.getRowCellConfig().setFieldGetter(Fruit::getName);

//        listData.getRowCellConfig().setCellFactory(c -> c.createDefaultCell().setRenderer(r -> {
//            r.valuePanel.innerHTML = "<b>" + r.value + "</b>";
//        }));
        listData.getRowCellConfig().setCellRenderer(r ->
                r.valuePanel.innerHTML = "<b>" + r.value + "</b>"
        );
        listData.setRowsData(Fruit.getFruits(2000));
        listData.drawData();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static ListAppView instance;


    public static ListAppView getInstance() {
        if (instance == null) instance = new ListAppView();
        return instance;
    }

}
