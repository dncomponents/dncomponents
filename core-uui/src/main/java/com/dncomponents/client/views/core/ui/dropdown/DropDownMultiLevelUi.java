package com.dncomponents.client.views.core.ui.dropdown;

import com.dncomponents.client.views.core.pcg.ComponentUi;

/**
 * @author nikolasavic
 */
public interface DropDownMultiLevelUi extends ComponentUi<DropDownView> {
    DropDownItemView getDropDownItemView();

    DropDownItemMultiLevelParentView getDropDownItemMultiLevelParentView();

    DropDownTreeNodePanelView getDropDownTreeNodePanelView();
}