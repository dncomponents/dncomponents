package com.dncomponents.client.views.core.ui.table.headers;

import com.google.gwt.user.client.ui.HasValue;

/**
 * @author nikolasavic
 */
public interface CheckBoxHeaderTableCellView extends HeaderTableTextCellView {
    HasValue<Boolean> getCheckBox();
}
