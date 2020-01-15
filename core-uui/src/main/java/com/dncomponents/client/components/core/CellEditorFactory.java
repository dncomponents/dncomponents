package com.dncomponents.client.components.core;
/**
 * @author nikolasavic
 */
public interface CellEditorFactory<T, M> {
    CellEditor<M> getCellEditor(T m);
}
