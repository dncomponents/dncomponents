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
import com.dncomponents.client.components.checkbox.CheckBoxSelectionGroup;
import com.dncomponents.client.components.checkbox.RadioSelectionGroup;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.table.header.HeaderGrouping;
import com.dncomponents.client.components.table.header.SortingDirection;
import com.dncomponents.client.components.table.header.filter.FilterPanelList;
import com.dncomponents.client.main.testing.Person;
import com.dncomponents.client.main.testing.TestingHelper;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.Arrays;

import static com.dncomponents.client.main.testing.TestingHelper.getPeople;


public class TableGroupByAppView implements IsElement {
    private static IsElement instance;

    @UiField
    HTMLElement root;
    @UiField
    Table<Person> table;
    @UiField
    CheckBox colorChb;
    @UiField
    CheckBox activeChb;
    @UiField
    CheckBox ageChb;
    @UiField
    CheckBoxSelectionGroup<ItemId> columnsSelection;
    @UiField
    RadioSelectionGroup<ItemId> colorAscDescSelection;
    @UiField
    RadioSelectionGroup<ItemId> activeAscDescSelection;
    @UiField
    RadioSelectionGroup<ItemId> ageAscDescSelection;

    {
        HtmlBinder.create(TableGroupByAppView.class, this).bind();
    }

    public TableGroupByAppView() {
        init();
    }

    public static IsElement getInstance() {
        if (instance == null)
            instance = new TableGroupByAppView();
        return instance;
    }

    private void init() {

        table.setMultiSorting(true);

        ColumnConfig<Person, String> nameColumn =
                new ColumnConfig.Builder<Person, String>(person -> {
                    if (person == null)
                        return "";
                    else
                        return person.getName();
                }).setName("Name")
                        .setColumnWidth("200px")
                        .build();

        ColumnConfig<Person, Boolean> activeColumn =
                new ColumnConfig.Builder<>(Person::isActive, Person::setActive)
                        .setName("Active")
                        .setColumnWidth("100px")
                        .setClazz(Boolean.class)
                        .setGroupRowRenderer((value, groupedValues, htmlElement) ->
                                htmlElement.innerHTML = value + " - items: " + groupedValues.size() +
                                                        " last name: " + groupedValues.get(groupedValues.size() - 1))
                        .build();
        ///column
        ColumnConfig<Person, Integer> ageColumn =
                new ColumnConfig.Builder<>(Person::getAge, Person::setAge)
                        .setName("Age")
                        .setColumnWidth("100px")
                        .setClazz(Integer.class)
                        .build();
        ///end column
        final ColumnConfig<Person, String> colorColumn =
                new ColumnConfig.Builder<>(Person::getCurrentColor)
                        .setName("Color")
                        .setCellFactory(c ->
                                new TableCell<Person, String>()
                                        .setRenderer(r -> {
                                            r.valuePanel.style.background = r.value;
                                            r.valuePanel.innerHTML = r.value;
                                        }))
                        .setGroupRowRenderer((value, groupedValues, htmlElement) ->
                                htmlElement.innerHTML = value + " - items: " + groupedValues.size())
                        .setColumnWidth("250px")
                        .setFilterPanelFactory(() -> new FilterPanelList(Arrays.asList(TestingHelper.colors)))
                        .build();


        table.addColumn(nameColumn, activeColumn, ageColumn, colorColumn);
        table.setRowsData(getPeople(500));
        table.drawData();
        ///
        HeaderGrouping colorHeaderGrouping = new HeaderGrouping(colorColumn);
        HeaderGrouping activeHeaderGrouping = new HeaderGrouping(activeColumn);
        HeaderGrouping ageHeaderGrouping = new HeaderGrouping(ageColumn);
        addGrouping(colorChb, colorAscDescSelection, colorHeaderGrouping);
        addGrouping(activeChb, activeAscDescSelection, activeHeaderGrouping);
        addGrouping(ageChb, ageAscDescSelection, ageHeaderGrouping);
    }

    private void addGrouping(CheckBox<Object> checkBox, RadioSelectionGroup<ItemId> selectionGroup, HeaderGrouping headerGrouping) {
        checkBox.addValueChangeHandler(event -> {
            headerGrouping.setSortingDirection(event.getValue() ? isAsc(selectionGroup) : null);
            table.headerCellHolder.group(headerGrouping);
        });
        selectionGroup.getEntitySelectionModel().addSelectionHandler(event -> {
            if (checkBox.isValueTrue()) {
                headerGrouping.setSortingDirection(isAsc(event.getSelectedItem()));
                table.headerCellHolder.group(headerGrouping);
            }
        });
    }

    private SortingDirection isAsc(RadioSelectionGroup<ItemId> sel) {
        return isAsc(sel.getSelection().getUserObject());
    }

    private SortingDirection isAsc(ItemId iid) {
        return iid.getId().equals("asc") ? SortingDirection.ASCENDING : SortingDirection.DESCENDING;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }


}
