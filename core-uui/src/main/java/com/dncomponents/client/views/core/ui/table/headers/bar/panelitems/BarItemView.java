package com.dncomponents.client.views.core.ui.table.headers.bar.panelitems;

import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;

/**
 * @author nikolasavic
 */
public interface BarItemView extends View {
    void addToPanel(IsElement element);

    void addDeleteHandler(ClickHandler clickHandler);

    void setActionLabel(String text);

    void setActionAndOr();

    void setColumnName(String columnName);

    void clear();
}
