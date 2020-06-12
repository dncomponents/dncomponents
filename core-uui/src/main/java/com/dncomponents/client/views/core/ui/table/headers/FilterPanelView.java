package com.dncomponents.client.views.core.ui.table.headers;

import com.dncomponents.client.components.HasRowsDataList;
import com.dncomponents.client.components.core.CellEditor;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.table.header.filter.Comparator;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.pcg.View;


/**
 * @author nikolasavic
 */
public interface FilterPanelView<T> extends View {

    HasValue<Comparator> getComparatorHasValue();

    HasRowsDataList<Comparator> getComparatorHasRowsData();

    void addClearClickHandler(ClickHandler clickHandler);

    void showClearElement(boolean b);

    CellEditor<T> getValueComponent();

    void setValueComponent(CellEditor<T> valueWidget);


    default <T> void setValueComponent(Class<T> clazz) {
    }
}
