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

package com.dncomponents.client.components.table.columnclasses.rowexpandercolumn;


import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.TableCellRowExpander;
import com.dncomponents.client.components.core.RowDetailsRenderer;
import com.dncomponents.client.components.table.header.HeaderTableTextCell;


public class ColumnRowExpander<T> extends ColumnConfig<T, Object> {

    RowDetailsRenderer<T> rowDetailsRenderer;

    public ColumnRowExpander(RowDetailsRenderer<T> rowDetailsRenderer) {
        this();
        this.rowDetailsRenderer = rowDetailsRenderer;
    }

    public ColumnRowExpander() {
        super(t -> "");
        this.setColumnWidth("15px");
        this.setHeaderCellFactory(HeaderTableTextCell::new);
        this.setCellFactory(c -> new TableCellRowExpander<T, Object>().setRowDetailsRenderer(rowDetailsRenderer));
    }

    public ColumnRowExpander<T> setRowDetailsRenderer(RowDetailsRenderer<T> rowDetailsRenderer) {
        this.rowDetailsRenderer = rowDetailsRenderer;
        return this;
    }
}
