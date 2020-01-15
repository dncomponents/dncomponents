package com.dncomponents.client.components.table.header.filter;

import com.dncomponents.client.views.IsElement;

public interface HasFilterValue<T> extends IsElement {
    void setValue(T userEnteredValue, Comparator comparator);

    void setFilterValueHandler(FilterValueHandler<T> handler);
}
