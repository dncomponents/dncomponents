package com.dncomponents.client.components.list;

import com.dncomponents.client.components.ListData;
import com.dncomponents.client.components.core.CellContext;
import com.dncomponents.client.components.core.CellFactory;
/**
 * @author nikolasavic
 */
public interface ListCellFactory<T, M> extends CellFactory<T, M, ListData<T, M>> {
    @Override
    ListCell<T, M> getCell(CellContext<T, M, ListData<T, M>> c);
}
