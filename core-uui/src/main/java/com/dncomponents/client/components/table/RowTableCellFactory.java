package com.dncomponents.client.components.table;

import com.dncomponents.client.components.Table;
import com.dncomponents.client.components.core.CellFactory;
import com.dncomponents.client.components.RowTable;
import com.dncomponents.client.components.core.CellContext;

import java.util.List;

public interface RowTableCellFactory<T> extends CellFactory<T, List, Table<T>> {
    @Override
    RowTable<T> getCell(CellContext<T, List, Table<T>> c);
}
