package com.dncomponents.client.components.table.columnclasses;

import com.dncomponents.client.components.AbstractFooterCell;
/**
 * @author nikolasavic
 */
public interface FooterCellFactory<T,M> {
    AbstractFooterCell<T, M> getCell();
}
