package com.dncomponents.client.views.core.ui.table.headers;

import com.dncomponents.client.components.core.HasText;
import com.dncomponents.client.components.table.header.SortingDirection;

/**
 * @author nikolasavic
 */
public interface HeaderTableSortCellView extends HeaderTableTextCellView, HasText {
    void setSorted(SortingDirection direction);

    boolean isActive();

    void setActive(boolean b);

    void setSortPresenter(SortPresenter presenter);

    void setSortIconText(String iconText);

    void setGroupOrder(int order);

    interface SortPresenter {
        void sort(SortingDirection currentDirection);
    }
}