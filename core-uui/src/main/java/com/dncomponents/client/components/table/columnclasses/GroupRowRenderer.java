package com.dncomponents.client.components.table.columnclasses;

import elemental2.dom.HTMLElement;

import java.util.List;

/**
 * Created by nikolasavic
 */
public interface GroupRowRenderer<T, M> {
    void render(M value, List<T> groupedValues, HTMLElement htmlElement);
}
