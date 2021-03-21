package com.dncomponents.client.components.table.header;

import com.dncomponents.client.components.EditFormDialog;
import com.dncomponents.client.components.RowTable;
import com.dncomponents.client.components.table.AbstractHeaderCell;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableEditCellView;

import java.util.function.Supplier;

/**
 * @author nikolasavic
 */
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
