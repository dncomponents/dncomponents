package com.dncomponents.client.components;

import java.util.List;

/**
 * @author nikolasavic
 */
public interface HasRowsDataList<T> extends HasRowsData<T> {
    void addRow(T t);

    void insertRow(T t, int index);

    void removeRow(T t);

    void setRowsData(List<T> rows);
}
