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

package com.dncomponents.client.components.table;

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.dncomponents.client.views.core.ui.table.TableUi;


public abstract class AbstractTableCell<T, M> extends BaseCell<T, M> {

    public AbstractTableCell() {
    }

    public AbstractTableCell(BaseCellView cellView) {
        super(cellView);
    }

    @Override
    public BaseCellView getCellView() {
        return super.getCellView();
    }

    @Override
    public Table<T> getOwner() {
        return (Table<T>) super.getOwner();
    }

    @Override
    public ColumnConfig<T, M> getCellConfig() {
        return (ColumnConfig<T, M>) super.getCellConfig();
    }

    @Override
    protected TableUi getUi() {
        return (TableUi) super.getUi();
    }


    protected boolean isPopupEditing() {
        return getOwner().isEditable() && getOwner().isPopupEditing();
    }

}
