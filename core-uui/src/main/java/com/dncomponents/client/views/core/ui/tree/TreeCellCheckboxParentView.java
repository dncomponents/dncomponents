package com.dncomponents.client.views.core.ui.tree;

import com.google.gwt.user.client.ui.HasValue;

/**
 * @author nikolasavic
 */
public interface TreeCellCheckboxParentView extends ParentTreeCellView {

    HasValue<Boolean> getCheckBox();

    void setIndeterminate(boolean b);
}
