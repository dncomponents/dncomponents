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

package com.dncomponents.client.components.table.header;

import com.dncomponents.client.components.EditFormDialog;
import com.dncomponents.client.components.RowTable;
import com.dncomponents.client.components.table.AbstractHeaderCell;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableEditCellView;

import java.util.function.Supplier;


public class HeaderTableEditHeader<T> extends AbstractHeaderCell {

    private Supplier<T> supplier;

    private boolean dialogMode;

    public HeaderTableEditHeader() {
    }

    public HeaderTableEditHeader(HeaderTableEditCellView headerCellView) {
        super(headerCellView);
    }

    public HeaderTableEditHeader<T> setSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
        return this;
    }

    @Override
    protected void bind() {
        getCellView().addAddHandler(e -> {
            if (!isDialogMode()) {
                RowTable<T> rowTable = getOwner().createTempCell(supplier.get());
                getOwner().insertTempRow(rowTable);
                rowTable.fromRow = true;
                rowTable.startEditing();
                getOwner().resetScrollTo(0d);
            } else {
                final EditFormDialog<Object> dialog = new EditFormDialog<>(getOwner().createTempCell(supplier.get()));
                dialog.setAddMode(true);
                dialog.show();
            }
        });
    }

    public HeaderTableEditHeader<T> setDialogMode(boolean dialogMode) {
        this.dialogMode = dialogMode;
        return this;
    }

    public boolean isDialogMode() {
        return dialogMode;
    }

    @Override
    public void draw() {

    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getHeaderTableEditCellView();
    }

    @Override
    protected HeaderTableEditCellView getCellView() {
        return (HeaderTableEditCellView) super.getCellView();
    }
}
