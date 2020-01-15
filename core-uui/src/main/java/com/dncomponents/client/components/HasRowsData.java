package com.dncomponents.client.components;

import java.util.List;

public interface HasRowsData<T> {

    List<T> getRowsData();

    void addRow(T t);

    void insertRow(T t, int index);

    void removeRow(T t);

    void setRowsData(List<T> rows);

    void drawData();

}
