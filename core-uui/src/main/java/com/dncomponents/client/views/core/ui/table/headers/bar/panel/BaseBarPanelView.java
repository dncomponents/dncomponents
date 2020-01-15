package com.dncomponents.client.views.core.ui.table.headers.bar.panel;


import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.table.header.HeaderWithModifiers;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;

import java.util.List;

/**
 * @author nikolasavic
 */
public interface BaseBarPanelView<T extends HeaderWithModifiers> extends View {

    void setColor(String barColor);

    void setDropDownTitle(String dropDownTitle);

    void addBarItem(IsElement barItem);

    void initColumns(List<ColumnConfig> columns);

    void clear();

    void setPresenter(Presenter<T> sortBarPanel);

    void update(int size);

    IsElement getLabel();

    interface Presenter<T extends HeaderWithModifiers> {
        void modify(T modifier);

        void loadColumnsDropDown();

        void addItemToBarPanel(ColumnConfig column);
    }

}
