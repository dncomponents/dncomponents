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
