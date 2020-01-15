package com.dncomponents.client.components;

import com.dncomponents.client.components.core.CellRenderer;

public abstract class BaseBuilder<T, M, C extends BaseCell<T, M>> {
    private C cell;

    public BaseBuilder() {
    }

    public BaseBuilder<T, M, C> setRenderer(CellRenderer<T, M> cellRenderer) {
        getCell().setRenderer(cellRenderer);
        return this;
    }

    public BaseBuilder setEditable(boolean editable) {
        getCell().setEditable(editable);
        return this;
    }

    public C build() {
        return getCell();
    }

    protected C getCell() {
        if (cell == null) cell = cell();
        return cell;
    }

    protected abstract C cell();

}