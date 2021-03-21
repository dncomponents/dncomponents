package com.dncomponents.client.components.core.selectionmodel;

import java.util.List;

public interface MultiSelectionModel<T> extends BaseSelectionModel<T, List<T>> {


    void setSelected(List<T> models, boolean b, boolean fireEvent);

    @Override
    default boolean isSelected(T value) {
        return getSelection().contains(value);
    }
}
