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
import com.dncomponents.client.components.AbstractFooterCell;
import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.table.columnclasses.TableCellDate;
import com.dncomponents.client.components.table.footer.FooterCell;
import com.dncomponents.client.components.table.footer.NumberFooterCell;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.main.testing.Person;
import com.dncomponents.client.main.testing.TestingHelper;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TableHeaderAndFooterAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    Table<Person> table;


    public TableHeaderAndFooterAppView() {
        HtmlBinder.create(TableHeaderAndFooterAppView.class, this).bind();
        init();
    }


    private void init() {
        final ArrayList<Person> people = TestingHelper.getPeople(300);

        table.setHeaderRenderer((headerRow, headerCells, table) -> {
            //language=html
            HTMLElement tr = DomUtil.createTr();
            HTMLElement td1 = headerCells.get(0).asElement();
            HTMLElement td2 = DomUtil.createTd();
            td1.setAttribute("rowspan", "2");
            td1.style.verticalAlign = "middle";
            td2.setAttribute("colspan", "4");
            td2.innerHTML = "Second group";
            tr.appendChild(td1);
            tr.appendChild(td2);
            headerRow.appendChild(tr);
            for (int i = 1; i < headerCells.size(); i++) {
                headerRow.appendChild(headerCells.get(i).asElement());
            }
        });
        table.setFooterRenderer(new Table.FooterRenderer<Person>() {
            @Override
            public void render(HTMLElement headerRow, List<AbstractFooterCell> footerCells, Table<Person> table) {
                HTMLElement tr = DomUtil.createTr();
                HTMLElement td1 = footerCells.get(0).asElement();
                HTMLElement td2 = DomUtil.createTd();
                td1.setAttribute("rowspan", "2");
                td1.style.verticalAlign = "middle";
                td2.setAttribute("colspan", "4");
                td2.innerHTML = "Second group";
                tr.appendChild(td1);
                tr.appendChild(td2);
                headerRow.appendChild(tr);
                for (int i = 1; i < footerCells.size(); i++) {
                    headerRow.appendChild(footerCells.get(i).asElement());
                }

            }
        });

        ColumnConfig<Person, String> nameColumn =
                new ColumnConfig.Builder<>(Person::getName, Person::setName)
                        .setClazz(String.class)
                        .setName("Name")
                        .setColumnWidth("300px")
                        .setFooterCellFactory(() -> new FooterCell<Person, String>()
                                .setCellRenderer((valuePanel, cell) ->
                                        valuePanel.innerHTML = "items number: " + cell.getColumnItems().size()
                                ))
                        .build();

        ColumnConfig<Person, Boolean> activeColumn =
                new ColumnConfig.Builder<>(Person::isActive, Person::setActive)
                        .setClazz(Boolean.class)
                        .setName("Active")
                        .setColumnWidth("100px")
                        .setCellRenderer(r -> r.valuePanel.innerHTML = r.value + "")
                        .setEditable(true)
                        .build();


        ColumnConfig<Person, Integer> ageColumn =
                new ColumnConfig.Builder<>(Person::getAge, Person::setAge)
                        .setClazz(Integer.class)
                        .setName("Age")
                        .setColumnWidth("100px")
                        .setFooterCellFactory(() ->
                                new NumberFooterCell(NumberFooterCell.Types.COUNT, Object::toString))
                        .build();

        ColumnConfig<Person, String> colorColumn =
                new ColumnConfig.Builder<>(Person::getCurrentColor, Person::setCurrentColor)
                        .setName("color")
                        .setClazz(String.class)
                        .setCellFactory(c -> new TableCell<Person, String>()
                                .setRenderer(r -> {
                                    r.valuePanel.style.background = r.value;
                                    r.valuePanel.innerHTML = r.value;
                                }))
                        .setColumnWidth("250px")
                        .setFooterCellFactory(() ->
                                new FooterCell<Person, String>()
                                        .setCellRenderer((valuePanel, cell) -> {
                                            valuePanel.style.background = "lightgreen";
                                            valuePanel.style.color = "white";
                                            valuePanel.textContent = "Custom footer";
                                        }))
                        .build();

        ColumnConfig<Person, Date> dateColumn =
                new ColumnConfig.Builder<>(Person::getDate)
                        .setClazz(Date.class)
                        .setName("Date")
                        .setCellFactory(c -> new TableCellDate())
                        .setColumnWidth("300px")
                        .build();

        table.addColumn(nameColumn, activeColumn, ageColumn, colorColumn, dateColumn);
        table.setRowsData(people);
        table.drawData();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static TableHeaderAndFooterAppView instance;

    public static TableHeaderAndFooterAppView getInstance() {
        if (instance == null)
            instance = new TableHeaderAndFooterAppView();
        return instance;
    }


}
