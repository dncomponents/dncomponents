package com.dncomponents.client.components.table.columnclasses.rowexpandercolumn;

import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author nikolasavic
 */
public interface RowDetailsCellView extends BaseCellView {
    void showRow(HasWidgets insertedRow, IsWidget rowDetailsWidget, int columnSize);

    void hideRow();

    void setOpened(boolean b);
}
