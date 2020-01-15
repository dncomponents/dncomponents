package com.dncomponents.client.components.table.header.filter;
/**
 * @author nikolasavic
 */
public interface FilterValueHandler<T> {
    void selected(T userEnteredValue, Comparator comparator);
}
