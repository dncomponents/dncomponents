package com.dncomponents.client.views.core.ui.table.headers;


import com.dncomponents.client.components.core.events.value.HasValue;

/**
 * @author nikolasavic
 */
public interface CheckBoxHeaderTableCellView extends HeaderTableTextCellView {
    HasValue<Boolean> getCheckBox();
}
