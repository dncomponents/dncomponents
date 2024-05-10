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
import com.dncomponents.client.components.pager.Pager;
import com.dncomponents.client.components.pager.PagerWithList;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class TablePaginationAppView implements IsElement {
    @UiField
    HTMLElement root;
    @UiField
    Table<Fruit> table1;
    @UiField
    Table<Fruit> table2;


    public TablePaginationAppView() {
        HtmlBinder.create(TablePaginationAppView.class, this).bind();
        init1();
        init2();
    }

    //TODO fix: when sorting is pressed changing page doesn't preserve sorting state.
    private void init1() {
        table1.setRowsData(Fruit.getFruits(11500));
        table1.setPager(new Pager());

        ColumnConfig<Fruit, String> nameColumn =
                new ColumnConfig<>(Fruit::getName, Fruit::setName, "Name");
        ColumnConfig<Fruit, String> descColumn =
                new ColumnConfig<>(Fruit::getDescription, Fruit::setDescription, "Description");

        table1.addColumn(nameColumn);
        table1.addColumn(descColumn);

        table1.drawData();
    }

    private void init2() {
        table2.setRowsData(Fruit.getFruits(11500));
        table2.setPager(new PagerWithList());
        ColumnConfig<Fruit, String> nameColumn =
                new ColumnConfig<>(Fruit::getName, Fruit::setName, "Name");
        ColumnConfig<Fruit, String> descColumn =
                new ColumnConfig<>(Fruit::getDescription, Fruit::setDescription, "Description");

        table2.addColumn(nameColumn);
        table2.addColumn(descColumn);
        table2.drawData();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static TablePaginationAppView instance;

    public static TablePaginationAppView getInstance() {
        if (instance == null)
            instance = new TablePaginationAppView();
        return instance;
    }
}
