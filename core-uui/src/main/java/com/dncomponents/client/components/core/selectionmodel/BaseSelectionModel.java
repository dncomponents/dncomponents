package com.dncomponents.client.components.core.selectionmodel;

import java.util.List;

public interface BaseSelectionModel<T> {

    boolean setSelected(T model, boolean b, boolean fireEvent);

    default boolean setSelected(T model, boolean b) {
        return setSelected(model, b, false);
    }

    boolean isSelected(T value);

    List<T> getItems();

}