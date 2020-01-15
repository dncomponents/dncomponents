package com.dncomponents.client.views.core.ui.table.headers;


import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.table.header.HeaderFiltering;
import com.dncomponents.client.components.table.header.SortingDirection;
import com.dncomponents.client.components.table.header.filter.FilterValueHandler;

/**
 * @author nikolasavic
 */
public interface HeaderTableMenuCellView extends HeaderTableSortCellView {

    void setColumn(ColumnConfig column);

    void setGroupedBy(SortingDirection direction);

    void setFiltered(HeaderFiltering b);

    void setPresenter(Presenter presenter);

    interface Presenter extends SortPresenter, FilterValueHandler {
        void groupBy(SortingDirection direction);
    }

}
