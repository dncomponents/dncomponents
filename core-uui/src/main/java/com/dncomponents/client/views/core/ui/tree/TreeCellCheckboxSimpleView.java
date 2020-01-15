package com.dncomponents.client.views.core.ui.tree;

import com.google.gwt.user.client.ui.HasValue;

/**
 * @author nikolasavic
 */
public interface TreeCellCheckboxSimpleView extends BaseTreeCellView {
    HasValue<Boolean> getCheckBox();
}