package com.dncomponents.client.components.core.selectionmodel;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;

public interface SingleSelectionModel<T> extends BaseSelectionModel<T>,
        HasSelectionHandlers<T> {

    T getSelection();

    @Override
    default boolean isSelected(T value) {
        return getSelection() == null || value == null ? false : getSelection().equals(value);
    }
}
