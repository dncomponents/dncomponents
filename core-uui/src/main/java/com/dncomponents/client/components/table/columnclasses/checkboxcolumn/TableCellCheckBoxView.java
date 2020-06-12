package com.dncomponents.client.components.table.columnclasses.checkboxcolumn;

import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;

/**
 * @author nikolasavic
 */
public interface TableCellCheckBoxView extends BaseCellView {
    HasValue<Boolean> getCheckbox();
}
