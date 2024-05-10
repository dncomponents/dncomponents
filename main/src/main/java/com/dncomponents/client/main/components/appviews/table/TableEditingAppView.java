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
import com.dncomponents.client.components.checkbox.RadioSelectionGroup;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.components.core.validation.validators.EmptyValidator;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.table.columnclasses.TableCellDate;
import com.dncomponents.client.components.table.columnclasses.editcolumn.ColumnEdit;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.main.testing.Person;
import com.dncomponents.client.main.testing.TestingHelper;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.ArrayList;
import java.util.Date;


public class TableEditingAppView implements IsElement {
    private static TableEditingAppView instance;

    @UiField
    HTMLElement root;
    @UiField
    Table<Person> tableEditors;
    @UiField
    TextArea eventsTa;
    @UiField
    RadioSelectionGroup<ItemId> chooseEditingRadioGroup;

    {
        HtmlBinder.create(TableEditingAppView.class, this).bind();
    }

    public TableEditingAppView() {
        initDefaultEditors();
    }


    ColumnEdit<Person> columnEdit = new ColumnEdit<>(Person::new, false);

    private void initDefaultEditors() {

        final ArrayList<Person> people = TestingHelper.getPeople(3);

        chooseEditingRadioGroup.getEntitySelectionModel().addSelectionHandler(event -> {
            final String id = event.getSelectedItem().getId();
            if (id.equals("cell")) {
                tableEditors.setPopupEditing(false);
                tableEditors.removeColumn(columnEdit);
                tableEditors.drawData();
            } else if (id.equals("dialog")) {
                tableEditors.setPopupEditing(true);
                tableEditors.removeColumn(columnEdit);
                tableEditors.drawData();
            } else if (id.equals("row")) {
                tableEditors.removeColumn(columnEdit);
                columnEdit = new ColumnEdit<>(Person::new, false);
                tableEditors.addColumn(columnEdit);
                tableEditors.setPopupEditing(false);
                tableEditors.drawData();
            } else if (id.equals("rowDialog")) {
                tableEditors.removeColumn(columnEdit);
                columnEdit = new ColumnEdit<>(Person::new, true);
                tableEditors.addColumn(columnEdit);
                tableEditors.drawData();
            }
        });

        tableEditors.addRowValueChangedHandler(event -> {
            eventsTa.append("value changed: " + event.getRow().getModel().toString() + "\n");
            for (TableCell<Person, ?> changedCell : event.getChangedCells()) {
                eventsTa.append("column: " + changedCell.getCellConfig().getName() + " (old value: "
                                + changedCell.oldValue() + " - new value: " + changedCell.getValue() + ")\n");
            }
        });
        tableEditors.addModelChangedHandler(event ->
                eventsTa.append("model changed: " + event.getModel().toString() + "\n"));

        tableEditors.addCellValueChangedHandler(event -> {
            eventsTa.append("column: " + event.getCell().getCellConfig().getName() + " (old value: "
                            + event.getOldValue() + " - new value: " + event.getNewValue() + ")\n");
            eventsTa.append("cell changed: " + event.getCell().getModel().toString() + "\n");
        });
        ColumnConfig<Person, String> nameColumn = new ColumnConfig.Builder<>(Person::getName, Person::setName)
                .setName("Name")
                .setColumnWidth("70px")
                .setEditable(true)
                .setValidText("Looks good")
                .setValidator(new EmptyValidator<>())
                .setClazz(String.class)
                .build();

        ColumnConfig<Person, Boolean> activeColumn = new ColumnConfig.Builder<>(Person::isActive, Person::setActive)
                .setName("active")
                .setColumnWidth("50px")
                .setClazz(Boolean.class)
                .setEditable(true)
                .build();

        ///column
        ColumnConfig<Person, Integer> ageColumn = new ColumnConfig.Builder<>(Person::getAge, Person::setAge)
                .setName("age")
                .setColumnWidth("50px")
                .setClazz(Integer.class)
                .setEditable(true)
                .build();
        ///end column
        AutoCompleteEditor<String> acEditor = new AutoCompleteEditor<>(TestingHelper.getColors());
        ColumnConfig<Person, String> colorColumn =
                new ColumnConfig.Builder<>(Person::getCurrentColor, Person::setCurrentColor)
                        .setCellEditorFactory(m -> new AutoCompleteEditor<>(TestingHelper.getColors()))
                        .setClazz(String.class)
                        .setName("Color")
                        .setCellFactory(c ->
                                new TableCell<Person, String>()
                                        .setRenderer(r -> {
                                            r.valuePanel.style.background = r.value;
                                            r.valuePanel.textContent = r.value;
                                        }))
                        .setEditable(true)
                        .setColumnWidth("100px")
                        .build();
        colorColumn.setValidator(s -> {
            if (s.contains("red"))
                throw new ValidationException("Wrong answer!");
        });
        ColumnConfig<Person, Date> dateColumn =
                new ColumnConfig.Builder<>(Person::getDate, Person::setDate)
                        .setName("Date")
                        .setCellFactory(c -> new TableCellDate<>())
                        .setColumnWidth("50px")
                        .setEditable(true)
                        .setClazz(Date.class)
                        .build();

        tableEditors.addColumn(nameColumn, activeColumn, ageColumn, colorColumn, dateColumn, columnEdit);
        tableEditors.setRowsData(people);
        tableEditors.drawData();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }


    public static TableEditingAppView getInstance() {
        if (instance == null)
            instance = new TableEditingAppView();
        return instance;
    }

}
