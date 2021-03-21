package com.dncomponents.client.components.core.selectionmodel;

public interface SingleSelectionModel<T> extends BaseSelectionModel<T, T> {
    @Override
    default boolean isSelected(T value) {
        return getSelection() == null || value == null ? false : getSelection().equals(value);
    }
}
