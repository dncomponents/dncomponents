package com.dncomponents.client.components.core;

import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.components.BaseCell;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author nikolasavic
 */
public class CellConfig<T, M> {

    protected Function<T, M> fieldGetter;
    protected BiConsumer<T, M> fieldSetter;
    protected CellFactory<T, M, ? extends AbstractCellComponent<T, ?, ?>> cellFactory;
    private Class clazz; //TODO remove

    public CellConfig(Function<T, M> fieldGetter) {
        this.fieldGetter = fieldGetter;
    }

    public CellConfig(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
        this.fieldGetter = fieldGetter;
        this.fieldSetter = fieldSetter;
    }

    public BiConsumer<T, M> getFieldSetter() {
        return fieldSetter;
    }

    public <C extends CellConfig<T, M>> C setFieldSetter(BiConsumer<T, M> fieldSetter) {
        this.fieldSetter = fieldSetter;
        return (C) this;
    }

    public <C extends CellFactory<T, M, ? extends AbstractCellComponent<T, ?, ?>>> C getCellFactory() {
        if (cellFactory == null) throw new RuntimeException("Cell Factory is not defined!");
        return (C) cellFactory;
    }

    public <C extends CellConfig<T, M>> C setCellFactory(CellFactory<T, M, ? extends AbstractCellComponent<T, ?, ?>> cellFactory) {
        this.cellFactory = cellFactory;
        return (C) this;
    }

    public Function<T, M> getFieldGetter() {
        return fieldGetter;
    }

    public <C extends CellConfig<T, M>> C setFieldGetter(Function<T, M> fieldGetter) {
        this.fieldGetter = fieldGetter;
        return (C) this;
    }

    public <C extends CellConfig<T, M>> C setClazz(Class clazz) {
        this.clazz = clazz;
        return (C) this;
    }

    public Class getClazz() { //TODO remove
        return clazz;
    }

    protected BaseCell.BaseCellBuilder<T, M, ?> builder;

    public BaseCell.BaseCellBuilder<T, M, ?> getCellBuilder() {
        return builder;
    }
}