package com.dncomponents.client.components.table.header.bar;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.table.header.HeaderCellHolder;
import com.dncomponents.client.components.table.header.HeaderGrouping;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.GroupByBarPanelUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.GroupByBarPanelView;

/**
 * @author nikolasavic
 */
public class GroupByBarPanel extends BaseBarPanel<HeaderGrouping, GroupByBarPanelView> implements GroupByBarPanelView.Presenter {

    private static String DROP_DOWN_TITLE = "Columns to group";


    public GroupByBarPanel(HeaderCellHolder cellHolder, GroupByBarPanelUi pui) {
        super(pui, cellHolder);
        init();
    }

    private void init() {
        view.getRootView().setDropDownTitle(DROP_DOWN_TITLE);
        headerCellHolder.addGroupByHandler(event -> {
            view.getRootView().clear();
            view.getRootView().update(event.getModifiers().size());
            boolean first = true;
            for (HeaderGrouping headerGrouping : event.getModifiers()) {
                DirectionBarItem directionBarItem =
                        new DirectionBarItem(getUi().getSortBarItemView(), headerGrouping, this, first ? "Group by" : "then by");
                view.getRootView().addBarItem(directionBarItem);
                first = false;
            }
        });
        view.getRootView().addExpandAllHandler(event -> {
            headerCellHolder.getTable().groupBy.expandAll(event.getValue());
            headerCellHolder.getTable().groupBy.drawData();
        });
    }

    @Override
    public void modify(HeaderGrouping modifier) {
        headerCellHolder.group(modifier);
    }

    @Override
    public void addItemToBarPanel(ColumnConfig column) {
        headerCellHolder.group(new HeaderGrouping(column));
    }

    @Override
    public void expandAll(boolean b) {

    }


    protected GroupByBarPanelUi getUi() {
        return (GroupByBarPanelUi) super.getView();
    }
}