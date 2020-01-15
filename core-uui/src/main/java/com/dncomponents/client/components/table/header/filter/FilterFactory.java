package com.dncomponents.client.components.table.header.filter;
/**
 * @author nikolasavic
 */
public interface FilterFactory<T> {
    HasFilterValue<T> getHasFilterValue();
}