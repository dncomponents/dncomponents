package com.dncomponents.client.components.core;

import com.dncomponents.client.components.AbstractCellComponent;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author nikolasavic
 */
public class CellConfig<T, M> extends FieldConfig<T, M> {

    protected CellFactory<T, M, ? extends AbstractCellComponent<T, ?, ?>> cellFactory;
    protected CellRenderer<T, M> cellRenderer = r -> r.valuePanel.innerHTML = r.value == null ? "" : r.value + "";

    public CellConfig(Function<T, M> fieldGetter) {
        super(fieldGetter);
    }

    public CellConfig(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
        super(fieldGetter, fieldSetter);
    }

    public BiConsumer<T, M> getFieldSetter() {
        return fieldSetter;
    }

    public <C extends CellFactory<T, M, ? extends AbstractCellComponent<T, ?, ?>>> C getCellFactory() {
        if (cellFactory == null) throw new RuntimeException("Cell Factory is not defined!");
        return (C) cellFactory;
    }

    public void setCellFactory(CellFactory<T, M, ? extends AbstractCellComponent<T, ?, ?>> cellFactory) {
        this.cellFactory = cellFactory;
    }

    public void setCellRenderer(CellRenderer<T, M> cellRenderer) {
        this.cellRenderer = cellRenderer;
    }

    public CellRenderer<T, M> getCellRenderer() {
        return cellRenderer;
    }
}
