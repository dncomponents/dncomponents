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
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.core.entities.RowItemId;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.table.header.HeaderTableFilterCell;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.MouseEnterHandler;
import com.dncomponents.client.dom.handlers.MouseLeaveHandler;
import com.dncomponents.client.dom.handlers.MouseOverHandler;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class TableAppView implements IsElement {
    private static TableAppView instance;

    @UiField
    HTMLElement root;
    @UiField
    Table<Fruit> tableRenderer;
    @UiField
    Table<Fruit> eventsTable;
    @UiField
    TextArea rowEventsTa;
    @UiField
    TextArea cellEventsTa;
    @UiField
    public Table<RowItemId> binderTable;
    @UiField
    public TextArea textArea;

    public TableAppView() {
        HtmlBinder.create(TableAppView.class, this).bind();
        initRenderer();
        initEvents();
        binderTable.addCellHandler((ClickHandler) mouseEvent -> {
            TableCell<RowItemId, ?> cell = binderTable.getCell(mouseEvent);
            textArea.append(((ItemId) cell.getValue()).getId() + "\n");
        });
        binderTable.getSelectionModel().addSelectionHandler(event -> {
            for (RowItemId item : event.getSelectedItem()) {
                textArea.append(item.getId() + "\n");
            }
        });
    }


    private void initEvents() {

        eventsTable.setRowsData(Fruit.getFruits(1500));
        eventsTable.addColumn(new ColumnConfig<>(Fruit::getDescription, Fruit::setDescription, "Description"));
        eventsTable.addColumn(new ColumnConfig<>(Fruit::getName, Fruit::setName, "Name"));
        //rows
        eventsTable.addRowHandler((MouseLeaveHandler) mouseEvent ->
                rowEventsTa.append("row left: " + eventsTable.getRowCell(mouseEvent).getModel().getName() + "\n"));
        eventsTable.addRowHandler((MouseEnterHandler) mouseEvent ->
                rowEventsTa.append("row entered: " + eventsTable.getRowCell(mouseEvent).getModel().getName() + "\n"));
        eventsTable.addRowHandler((ClickHandler) mouseEvent ->
                rowEventsTa.append("row clicked: " + eventsTable.getRowCell(mouseEvent).getModel().getName() + "\n"));
        //cells
        eventsTable.addCellHandler((ClickHandler) mouseEvent ->
                cellEventsTa.append("cell clicked: " + eventsTable.getCell(mouseEvent).getValue() + "\n"));
        eventsTable.addCellHandler((MouseOverHandler) mouseEvent ->
                cellEventsTa.append("cell mouse over event: " + eventsTable.getCell(mouseEvent).getValue() + "\n"));
        eventsTable.drawData();
    }

    private void initRenderer() {
        tableRenderer.setRowsData(Fruit.getFruits(1500));
        ColumnConfig<Fruit, String> nameColumn = new ColumnConfig.Builder<>(Fruit::getName, Fruit::setName)
                .setName("Name")
                .setHeaderCellFactory(() -> new HeaderTableFilterCell())
                .setColumnWidth("200px")
                .setVisible(true)
                .setClazz(String.class)
                .setCellRenderer(r ->
                        r.valuePanel.innerHTML = "<b>" + DomUtil.escapeHtml(r.value) + "</b>")
                .build();


        ColumnConfig<Fruit, String> descColumn =
                new ColumnConfig<>(Fruit::getDescription, Fruit::setDescription, "description");

        tableRenderer.addColumn(nameColumn);
        tableRenderer.addColumn(descColumn);

        tableRenderer.drawData();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }


    public static TableAppView getInstance() {
        if (instance == null)
            instance = new TableAppView();
        return instance;
    }
}
