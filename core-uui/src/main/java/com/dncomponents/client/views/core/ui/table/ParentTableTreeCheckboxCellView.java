package com.dncomponents.client.views.core.ui.table;

import com.dncomponents.client.components.core.events.value.HasValue;

/**
 * @author nikolasavic
 */
public interface ParentTableTreeCheckboxCellView extends ParentTableTreeCellView {
    HasValue<Boolean> getCheckBox();

    void setIndeterminate(boolean b);
}
