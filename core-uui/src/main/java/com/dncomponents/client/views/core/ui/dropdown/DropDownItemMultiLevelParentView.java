package com.dncomponents.client.views.core.ui.dropdown;

import com.dncomponents.client.dom.handlers.MouseEnterHandler;
import com.dncomponents.client.dom.handlers.MouseLeaveHandler;

/**
 * @author nikolasavic
 */
public interface DropDownItemMultiLevelParentView extends DropDownItemView {
    void addMouseLeaveHandler(MouseLeaveHandler mouseLeaveHandler);

    void addMouseEnterHandler(MouseEnterHandler mouseEnterHandler);
}