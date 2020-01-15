package com.dncomponents.client.views.core.ui.table.headers;

import com.dncomponents.client.components.HasRowsData;
import com.dncomponents.client.components.core.CellEditor;
import com.dncomponents.client.components.table.header.filter.Comparator;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.pcg.View;
import com.google.gwt.user.client.ui.HasValue;


/**
 * @author nikolasavic
 */
public interface FilterPanelView<T> extends View {

    HasValue<Comparator> getComparatorHasValue();

    HasRowsData<Comparator> getComparatorHasRowsData();

    void addClearClickHandler(ClickHandler clickHandler);

    void showClearElement(boolean b);

    CellEditor<T> getValueComponent();

    void setValueComponent(CellEditor<T> valueWidget);


    default <T> void setValueComponent(Class<T> clazz){};
}
