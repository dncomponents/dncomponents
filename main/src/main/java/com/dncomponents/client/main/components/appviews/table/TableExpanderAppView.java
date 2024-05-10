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

package com.dncomponents.client.main.components.appviews.table;

import com.dncomponents.UiField;
import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.components.TableCellRowExpander;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class TableExpanderAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    CheckBox<Object> singleExpand;
    @UiField
    Table<Fruit> table;

    public TableExpanderAppView() {
        HtmlBinder.create(TableExpanderAppView.class, this).bind();
        init();
    }

    private void init() {
        singleExpand.setValue(true);
        singleExpand.addValueChangeHandler(event -> table.setSingleExpandRow(event.getValue()));

        table.setRowsData(Fruit.getFruits(1500));

        ColumnConfig<Fruit, String> columnRowExpander = new ColumnConfig.Builder<>(Fruit::getName)
                .setCellFactory(c ->
                        new TableCellRowExpander<Fruit, String>()
                                .setRowDetailsRenderer((fruit, valuePanel)
                                        -> valuePanel.innerHTML = fruit.getName()
                                                                  + " - " + fruit.getDescription()))
                .setColumnWidth("20px")
                .build();

        ColumnConfig<Fruit, String> nameColumn =
                new ColumnConfig<>(Fruit::getName, Fruit::setName, "Name");

        ColumnConfig<Fruit, String> descColumn =
                new ColumnConfig<>(Fruit::getDescription, Fruit::setDescription, "Description");

        table.addColumn(columnRowExpander);
        table.addColumn(nameColumn);
        table.addColumn(descColumn);
        table.drawData();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static TableExpanderAppView instance;

    public static TableExpanderAppView getInstance() {
        if (instance == null)
            instance = new TableExpanderAppView();
        return instance;
    }


}
