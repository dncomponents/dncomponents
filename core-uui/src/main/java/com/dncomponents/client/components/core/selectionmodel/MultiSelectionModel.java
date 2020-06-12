package com.dncomponents.client.components.core.selectionmodel;

 import com.dncomponents.client.components.core.events.selection.HasSelectionHandlers;
 import com.dncomponents.client.components.core.events.value.HasValue;

 import java.util.List;

public interface MultiSelectionModel<T> extends BaseSelectionModel<T>,
        HasSelectionHandlers<List<T>> {

    List<T> getSelection();

    @Override
    default boolean isSelected(T value) {
        return getSelection().contains(value);
    }

    HasValue<List<T>> getHasValue();
}