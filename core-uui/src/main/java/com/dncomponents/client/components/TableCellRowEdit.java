/*
 * Copyright 2023 dncomponents
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

package com.dncomponents.client.components;

import com.dncomponents.client.components.core.events.hide.HideEvent;
import com.dncomponents.client.components.core.events.hide.HideHandler;
import com.dncomponents.client.components.modal.Dialog;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.table.columnclasses.editcolumn.TableCellEditView;

/**
 * @author nikolasavic
 */
public class TableCellRowEdit<T, M> extends TableCell<T, M> {
    private boolean dialogMode;

    @Override
    protected void bind() {
        getCellView().addOnCancelHandler(e -> {
            getCellView().setEditMode(false);
            this.getRowTable().stopEditing();
        });
        getCellView().addOnEditHandler(e -> {
            getCellView().setEditMode(true);
            if (!isDialogMode()) {
                this.getRowTable().fromRow = true;
                this.getRowTable().startEditing();
            } else {
                final EditFormDialog<T> dialog = new EditFormDialog<>(getRowTable());
                dialog.addHideHandler(event -> {
                    getCellView().setEditMode(false);
                });
                dialog.show();
            }
        });
        getCellView().addOnSaveHandler(e -> {
            if (this.getRowTable().saveChanges())
                getCellView().setEditMode(false);
        });
        getCellView().addOnDeleteHandler(e -> {
            Dialog dialog = new Dialog();
            dialog.setHeader(se -> se.textContent = "Confirm");
            dialog.setContent(se -> se.textContent = "Are you sure you want to delete this record?");
            dialog.addOKHandler(ev -> {
                this.getRowTable().delete();
                dialog.hide();
            }, "Delete");
            dialog.show();
        });
    }

    public TableCellRowEdit<T, M> setDialogMode(boolean dialogMode) {
        this.dialogMode = dialogMode;
        return this;
    }

    public boolean isDialogMode() {
        return dialogMode;
    }

    public void startEditing() {
        getCellView().setEditMode(true);
    }

    @Override
    public void stopEditing() {
        getCellView().setEditMode(false);
    }

    @Override
    public void draw() {

    }

    @Override
    public TableCellEditView getCellView() {
        return (TableCellEditView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTableCellEditView();
        getCellView().setEditMode(false);
    }
}
