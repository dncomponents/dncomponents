package com.dncomponents.client.components;

import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.list.ListCellFactory;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ListCellConfig<T, M> extends CellConfig<T, M> {

    public ListCellConfig() {
        super(t -> null);
        setCellFactory(c -> c.createDefaultCell());
    }

    public ListCellConfig(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
        super(fieldGetter, fieldSetter);
    }

    public void setCellFactory(ListCellFactory<T, M> cellFactory) {
        super.setCellFactory(cellFactory);
    }

    @Override
    public ListCellFactory<T, M> getCellFactory() {
        return super.getCellFactory();
    }

}
