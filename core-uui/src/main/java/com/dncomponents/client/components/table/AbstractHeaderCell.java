package com.dncomponents.client.components.table;


import com.dncomponents.client.components.table.header.HeaderCellHolder;
import com.dncomponents.client.views.core.pcg.cell.CellView;

/**
 * @author nikolasavic
 */
public abstract class AbstractHeaderCell<T> extends BaseTableCell<T, Object, CellView> {

    protected HeaderCellHolder headerCellHolder;

    public AbstractHeaderCell() {
    }

    public AbstractHeaderCell(CellView headerCellView) {
        super(headerCellView);
    }


    public int getColumn() {
        return getOwner().getColumnConfigs().indexOf(cellConfig);
    }

    public void setHeaderCellHolder(HeaderCellHolder cellHolder) {
        this.headerCellHolder = cellHolder;
    }

}
