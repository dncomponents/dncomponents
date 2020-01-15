package com.dncomponents.client.components.table;

import com.dncomponents.client.components.AbstractCell;
import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.views.core.pcg.cell.CellView;
import com.dncomponents.client.views.core.ui.table.TableUi;

public abstract class BaseTableCell<T, M, CW extends CellView> extends AbstractCell<T, M, CW> {

    public BaseTableCell() {
    }

    public BaseTableCell(CW cellView) {
        super(cellView);
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
}
