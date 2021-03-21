package com.dncomponents.client.components.core.selectionmodel;

import com.dncomponents.client.components.core.events.selection.HasSelectionHandlers;
import com.dncomponents.client.components.core.events.value.HasValueSelection;

import java.util.List;

public interface BaseSelectionModel<T, P> extends HasSelectionHandlers<P>, HasValueSelection<P> {

    boolean setSelected(T model, boolean b, boolean fireEvent);

    default boolean setSelected(T model, boolean b) {
        return setSelected(model, b, false);
    }

    boolean isSelected(T value);

    P getSelection();

    List<T> getItems();
}
