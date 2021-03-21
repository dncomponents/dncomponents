package com.dncomponents.client.components.table.header.bar;

import com.dncomponents.client.components.table.header.HeaderWithModifiers;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.views.core.ui.table.headers.bar.panelitems.BarItemView;

/**
 * Displays HeaderWithModifier view.
 *
 * @author nikolasavic
 */
public class BarItem<T extends HeaderWithModifiers, V extends BarItemView> extends BaseComponent<T, V> {

    protected BaseBarPanel baseBarPanel;

    public BarItem(V view, T header, BaseBarPanel baseBarPanel) {
        super(view);
        this.baseBarPanel = baseBarPanel;
        this.userObject = header;
        view.setColumnName(header.getColumn().getName());
        view.addDeleteHandler(mouseEvent -> delete());
    }

    protected void modified() {
        baseBarPanel.modify(userObject);
    }

    protected void delete() {
        userObject.setActiveModifier(null);
        baseBarPanel.modify(userObject);
    }

    protected BaseBarPanel getBarPanel() {
        return baseBarPanel;
    }
}
