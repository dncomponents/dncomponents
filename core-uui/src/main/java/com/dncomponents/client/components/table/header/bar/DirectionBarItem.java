package com.dncomponents.client.components.table.header.bar;


import com.dncomponents.client.components.table.header.HasDirection;
import com.dncomponents.client.components.table.header.HeaderWithModifiers;
import com.dncomponents.client.views.core.ui.table.headers.bar.panelitems.SortBarItemView;

/**
 * @author nikolasavic
 */
public class DirectionBarItem<T extends HeaderWithModifiers & HasDirection> extends BarItem<T, SortBarItemView> {

    public DirectionBarItem(SortBarItemView barItemView, T header, BaseBarPanel baseBarPanel, String text) {
        super(barItemView, header, baseBarPanel);
        view.setActionLabel(text);
        view.addSelectionHandler(event -> {
            header.setSortingDirection(event.getSelectedItem());
            modified();
        });
        view.setDirection(header.getSortingDirection());
    }

}
