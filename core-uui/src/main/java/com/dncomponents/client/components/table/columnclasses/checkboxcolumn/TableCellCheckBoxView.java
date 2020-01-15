package com.dncomponents.client.components.table.columnclasses.checkboxcolumn;

import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.google.gwt.user.client.ui.HasValue;

/**
 * @author nikolasavic
 */
public interface TableCellCheckBoxView extends BaseCellView {
    HasValue<Boolean> getCheckbox();
}
