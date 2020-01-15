package com.dncomponents.client.components.core;

/**
 * @author nikolasavic
 */
public interface CellEditFactory<M> {
    //It must return hasvalue,widget and focusable component!
    CellEditor<M> getCellEdit();
}