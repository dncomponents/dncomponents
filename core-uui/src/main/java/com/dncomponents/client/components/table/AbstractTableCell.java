package com.dncomponents.client.components.table;

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.CellEditing;
import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.dncomponents.client.views.core.ui.table.TableUi;

/**
 * Created by nikolasavic
 */
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
