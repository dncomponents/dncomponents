package com.dncomponents.client.components.core;

import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.components.BaseCell;

/**
 * @author nikolasavic
 */
public interface CellFactory<T, M, C extends AbstractCellComponent<T, ?, ?>> {
    BaseCell<T, M> getCell(CellContext<T, M, C> c);
}
