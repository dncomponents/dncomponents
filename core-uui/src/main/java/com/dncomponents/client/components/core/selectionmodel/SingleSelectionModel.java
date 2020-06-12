package com.dncomponents.client.components.core.selectionmodel;

import com.dncomponents.client.components.core.events.selection.HasSelectionHandlers;

public interface SingleSelectionModel<T> extends BaseSelectionModel<T>,
        HasSelectionHandlers<T> {

    T getSelection();

    @Override
    default boolean isSelected(T value) {
        return getSelection() == null || value == null ? false : getSelection().equals(value);
    }
}
