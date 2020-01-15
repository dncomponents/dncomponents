package com.dncomponents.client.views.core.ui.table.headers;

import com.dncomponents.client.views.IsElement;

/**
 * @author nikolasavic
 */
public interface HeaderTableFilterCellView extends HeaderTableSortCellView {

    void setFilterPanel(IsElement filterPanel);

    void setFilterIconVisible(boolean b);

}
