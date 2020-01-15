package com.dncomponents.client.components.core;

import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.components.BaseCell;
/**
 * @author nikolasavic
 */
public class CellContext<T, M, C extends AbstractCellComponent<T, ?, ?>> {

    public CellContext(CellConfig<T, M> cellConfig, CellFactory<T, M, C> defaultCellFactory, T model, C owner) {
        this.defaultCellFactory = defaultCellFactory;
        this.cellConfig = cellConfig;
        this.model = model;
        this.owner = owner;
    }

    CellConfig<T, M> cellConfig;
    public CellFactory<T, M, C> defaultCellFactory;
    public T model;
    public C owner;

    public M value() {
        return cellConfig.getFieldGetter().apply(model);
    }

    public <B extends BaseCell<T, M>> B createDefaultCell() {
        return (B) defaultCellFactory.getCell(this);
    }
}
