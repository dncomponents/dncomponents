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
import com.dncomponents.client.components.autocomplete.AutoCompleteEditor;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.table.columnclasses.TableCellDate;
import com.dncomponents.client.components.table.header.HeaderTableSortCell;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.main.testing.Person;
import com.dncomponents.client.main.testing.TestingHelper;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.ArrayList;
import java.util.Date;


public class TableSortingAppView implements IsElement {
    @UiField
    HTMLElement root;
    @UiField
    CheckBox<Object> multiSortingChb;
    @UiField
    Table<Fruit> table;
    @UiField
    Table tableDefault;


    public TableSortingAppView() {
        HtmlBinder.create(TableSortingAppView.class, this).bind();
        init();
        initDefaultComparators();
    }

    private void init() {

        multiSortingChb.addValueChangeHandler(event -> table.setMultiSorting(event.getValue()));

        ColumnConfig<Fruit, String> nameColumn =
                new ColumnConfig.Builder<>(Fruit::getName, Fruit::setName)
                        .setHeaderCellFactory(() -> new HeaderTableSortCell().setText("Name"))
                        .build();

        ColumnConfig<Fruit, String> descColumn =
                new ColumnConfig.Builder<>(Fruit::getDescription, Fruit::setDescription)
                        .setHeaderCellFactory(() -> new HeaderTableSortCell().setText("description"))
                        .build();

        table.addColumn(nameColumn);
        table.addColumn(descColumn);

        table.setRowsData(Fruit.getFruits(1000));
        table.drawData();
    }

    private void initDefaultComparators() {
        final ArrayList<Person> people = TestingHelper.getPeople(500);
        ColumnConfig<Person, String> nameColumn = new ColumnConfig.Builder<>(Person::getName, Person::setName)
                .setName("Name")
                .setColumnWidth("300px")
                .setClazz(String.class)
                .build();

        ColumnConfig<Person, Boolean> activeColumn = new ColumnConfig.Builder<>(Person::isActive, Person::setActive)
                .setName("active")
                .setColumnWidth("100px")
                .setClazz(Boolean.class)
                .build();

        ///column
        ColumnConfig<Person, Integer> ageColumn = new ColumnConfig.Builder<>(Person::getAge, Person::setAge)
                .setName("age")
                .setColumnWidth("100px")
                .setClazz(Integer.class)
                .build();
        ///end column
        AutoCompleteEditor<String> acEditor = new AutoCompleteEditor<>(TestingHelper.getColors());
        ColumnConfig<Person, String> colorColumn =
                new ColumnConfig.Builder<>(Person::getCurrentColor, Person::setCurrentColor)
                        .setClazz(String.class)
                        .setName("Color")
                        .setCellFactory(c ->
                                new TableCell<Person, String>()
                                        .setRenderer(r -> {
                                            r.valuePanel.style.background = r.value;
                                            r.valuePanel.textContent = r.value;
                                        }).setCellEditor(acEditor))
                        .setColumnWidth("250px")
                        .build();

        ColumnConfig<Person, Date> dateColumn =
                new ColumnConfig.Builder<>(Person::getDate, Person::setDate)
                        .setName("Date")
                        .setCellFactory(c -> new TableCellDate<>())
                        .setColumnWidth("300px")
                        .setClazz(Date.class)
                        .build();

        tableDefault.addColumn(nameColumn, activeColumn, ageColumn, colorColumn, dateColumn);
        tableDefault.setRowsData(people);
        tableDefault.drawData();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static TableSortingAppView instance;

    public static TableSortingAppView getInstance() {
        if (instance == null)
            instance = new TableSortingAppView();
        return instance;
    }


}
