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
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class TableColumnVisibilityAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    public HTMLElement leftPanel;
    @UiField
    Table<Fruit> table;

    public TableColumnVisibilityAppView() {
        HtmlBinder.create(TableColumnVisibilityAppView.class, this).bind();
        init();
    }

    private void init() {
        table.setRowsData(Fruit.getFruits(1500));
        ColumnConfig<Fruit, String> cellConfig1 = new ColumnConfig<>(Fruit::getName, Fruit::setName, "Name");
        ColumnConfig<Fruit, String> cellConfig2 = new ColumnConfig<>(Fruit::getDescription, Fruit::setDescription, "Description");
        table.addColumn(cellConfig1);
        table.addColumn(cellConfig2);
        table.drawData();
        for (ColumnConfig columnConfig : table.getColumnConfigs()) {
            CheckBox columnCheckBox = new CheckBox(columnConfig.getName());
            columnCheckBox.setValue(columnConfig.isVisible(), false);
            columnCheckBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> event) {
                    columnConfig.setVisible(event.getValue());
                    table.drawHeader();
                    table.drawData();
                }
            });
            leftPanel.appendChild(columnCheckBox.asElement());
        }
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static TableColumnVisibilityAppView instance;

    public static TableColumnVisibilityAppView getInstance() {
        if (instance == null)
            instance = new TableColumnVisibilityAppView();
        return instance;
    }


}
