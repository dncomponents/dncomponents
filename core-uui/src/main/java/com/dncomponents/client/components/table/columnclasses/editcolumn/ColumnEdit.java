package com.dncomponents.client.components.table.columnclasses.editcolumn;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.TableCellRowEdit;
import com.dncomponents.client.components.table.header.HeaderTableEditHeader;

import java.util.function.Supplier;

/**
 * @author nikolasavic
 */
public class ColumnEdit<T> extends ColumnConfig<T, Object> {

    public ColumnEdit(final Supplier<T> supplier, boolean dialogMode) {
        super(t -> t);
        this.setName("Edit");
        this.setColumnWidth("50px");
        this.setHeaderCellFactory(() -> new HeaderTableEditHeader<T>().setSupplier(supplier).setDialogMode(dialogMode));
        this.setCellFactory(c -> {
            c.owner.setCellEditMode(false);
            return new TableCellRowEdit<T, Object>().setDialogMode(dialogMode);
        });
    }


}
