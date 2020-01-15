package com.dncomponents.client.components.table.header.bar;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.table.header.HeaderCellHolder;
import com.dncomponents.client.components.table.header.HeaderSorting;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.SortBarPanelUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.SortBarPanelView;

/**
 * @author nikolasavic
 */
public class SortBarPanel extends BaseBarPanel<HeaderSorting, SortBarPanelView> {

    private static String DROP_DOWN_TITLE = "Columns to sort";

    public SortBarPanel(HeaderCellHolder cellHolder, SortBarPanelUi pui) {
        super(pui, cellHolder);
        init();
    }

    private void init() {
        view.getRootView().setDropDownTitle(DROP_DOWN_TITLE);
        headerCellHolder.addSortHandler(event -> {
            view.getRootView().clear();
            view.getRootView().update(event.getModifiers().size());
            boolean first = true;
            for (HeaderSorting headerSorting : event.getModifiers()) {
                DirectionBarItem directionBarItem =
                        new DirectionBarItem(getUi().getSortBarItemView(), headerSorting, this, first ? "Sorted by" : "then by");
                view.getRootView().addBarItem(directionBarItem);
                first = false;
            }
        });
    }

    @Override
    public void modify(HeaderSorting modifier) {
        headerCellHolder.sorted(modifier);
    }

    @Override
    public void addItemToBarPanel(ColumnConfig column) {
        headerCellHolder.sorted(new HeaderSorting(column));
    }

    //    @Override
    protected SortBarPanelUi getUi() {
        return (SortBarPanelUi) super.getView();
    }
}