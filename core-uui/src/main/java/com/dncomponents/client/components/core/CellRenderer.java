package com.dncomponents.client.components.core;


/**
 * @author nikolasavic
 */

public interface CellRenderer<T, M> {
    void setValue(RendererContext<T, M> r);
}