package com.dncomponents.client.components.table.footer;

import com.dncomponents.client.components.AbstractFooterCell;
/**
 * @author nikolasavic
 */
public class FooterCell<T, N> extends AbstractFooterCell<T, N> {

    public FooterCell() {
    }

    @Override
    public FooterCell<T, N> setCellRenderer(FooterCellRenderer<T, N> cellRenderer) {
        super.setCellRenderer(cellRenderer);
        return this;
    }

}
