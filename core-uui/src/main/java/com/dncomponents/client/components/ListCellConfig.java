package com.dncomponents.client.components;

import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.CellContext;
import com.dncomponents.client.components.list.ListCell;
import com.dncomponents.client.components.list.ListCellFactory;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ListCellConfig<T, M> extends CellConfig<T, M> {

    public ListCellConfig() {
        super(new Function<T, M>() {
            @Override
            public M apply(T t) {
                return null;
            }
        });
        setCellFactory(new ListCellFactory<T, M>() {
            @Override
            public ListCell<T, M> getCell(CellContext<T, M, ListData<T, M>> c) {
                return c.createDefaultCell().initWithBuilder(builder);
            }
        });
        builder = new ListCell.Builder<>();
    }

    public ListCellConfig(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
        super(fieldGetter, fieldSetter);
    }

    public void setCellFactory(ListCellFactory<T, M> cellFactory) {
        super.setCellFactory(cellFactory);
    }

    @Override
    public ListCellFactory<T, M> getCellFactory() {
        return (ListCellFactory<T, M>) super.getCellFactory();
    }

    @Override
    public ListCell.Builder<T, M> getCellBuilder() {
        return (ListCell.Builder<T, M>) super.getCellBuilder();
    }
}
