package com.dncomponents.client.components;

import java.util.List;

public interface HasRowsData<T> {

    List<T> getRowsData();

    void drawData();

    void refreshSelections();

}
