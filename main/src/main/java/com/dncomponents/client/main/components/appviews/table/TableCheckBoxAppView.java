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
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.components.table.columnclasses.checkboxcolumn.ColumnCheckBox;
import com.dncomponents.client.components.table.header.HeaderTableTextCell;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.List;
import java.util.stream.Collectors;


public class TableCheckBoxAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    public TextArea ta;
    @UiField
    Table<Fruit> table;


    public TableCheckBoxAppView() {
        HtmlBinder.create(TableCheckBoxAppView.class, this).bind();
        init();
    }

    private void init() {
        table.setRowsData(Fruit.getFruits(11500));
        ColumnConfig<Fruit, String> nameColumn = new ColumnConfig<>(Fruit::getName, Fruit::setName);
        nameColumn.setHeaderCellFactory(() -> new HeaderTableTextCell("name"));

        ColumnConfig<Fruit, String> descColumn = new ColumnConfig<>(Fruit::getDescription, Fruit::setDescription);
        descColumn.setHeaderCellFactory(() -> new HeaderTableTextCell("description"));

        ColumnCheckBox columnCheckBox = new ColumnCheckBox();

        table.addColumn(columnCheckBox);
        table.addColumn(nameColumn);
        table.addColumn(descColumn);
        table.getSelectionModel().addSelectionHandler((SelectionEvent<List<Fruit>> event) -> {
            ta.setValue(event.getSelectedItem().size() > 100 ?
                    event.getSelectedItem().size() + " items selected" :
                    event.getSelectedItem()
                            .stream()
                            .map(Fruit::getName)
                            .collect(Collectors.joining("\n")));
        });
        table.drawData();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static TableCheckBoxAppView instance;

    public static TableCheckBoxAppView getInstance() {
        if (instance == null)
            instance = new TableCheckBoxAppView();
        return instance;
    }

}
