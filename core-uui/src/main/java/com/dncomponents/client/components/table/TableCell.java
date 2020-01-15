package com.dncomponents.client.components.table;

import com.dncomponents.client.components.RowTable;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;

/**
 * @author nikolasavic
 */
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

//    @Override
//    public TableCell<T, M> initWithBuilder(BaseCellBuilder builder) {
//        return super.initWithBuilder(builder);
//    }

    @Override
    public boolean isEditable() {
        return super.isEditable() && getCellConfig().isEditable() && !isPopupEditing();
    }

    public static class Builder<T, M> extends AbstractTableCell.Builder<T, M> {

        @Override
        public TableCell<T, M> build() {
            return null;
        }
    }

}