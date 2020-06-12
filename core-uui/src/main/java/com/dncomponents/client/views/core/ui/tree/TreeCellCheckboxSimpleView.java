package com.dncomponents.client.views.core.ui.tree;

import com.dncomponents.client.components.core.events.value.HasValue;

/**
 * @author nikolasavic
 */
public interface TreeCellCheckboxSimpleView extends BaseTreeCellView {
    HasValue<Boolean> getCheckBox();
}