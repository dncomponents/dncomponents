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

import com.dncomponents.client.components.RowTable;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;


public class TableCell<T, M> extends AbstractTableCell<T, M> {

    RowTable rowTable;

    public TableCell() {
    }

    public TableCell(BaseCellView cellView) {
        super(cellView);
    }

    @Override
    protected void setSelection() {
        //We don't need selection cause RowTable has selection style
    }

    protected void setSelectionBase() {
        super.setSelection();
    }

    public RowTable getRowTable() {
        return rowTable;
    }

    public void setRowTable(RowTable rowTable) {
        this.rowTable = rowTable;
    }

    //row is same as RowTable
    public int getRow() {
        return rowTable.getRow();
    }

    @Override
    public Table<T> getOwner() {
        return super.getOwner();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTableCellView();
    }

    @Override
    public boolean isEditable() {
        return super.isEditable() && getCellConfig().isEditable() && !isPopupEditing();
    }

}
