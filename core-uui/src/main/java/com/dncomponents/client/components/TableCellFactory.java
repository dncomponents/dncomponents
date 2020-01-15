package com.dncomponents.client.components;


import com.dncomponents.client.components.core.CellFactory;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.core.CellContext;

/**
 * @author nikolasavic
 */
public interface TableCellFactory<T, M> extends CellFactory<T, M, Table<T>> {
    @Override
    TableCell<T, M> getCell(CellContext<T, M, Table<T>> c);
}
