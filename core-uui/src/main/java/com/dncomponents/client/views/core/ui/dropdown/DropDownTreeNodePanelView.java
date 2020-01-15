package com.dncomponents.client.views.core.ui.dropdown;

import com.dncomponents.client.dom.handlers.MouseEnterHandler;
import com.dncomponents.client.dom.handlers.MouseLeaveHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;

/**
 * @author nikolasavic
 */
public interface DropDownTreeNodePanelView extends View {
    void add(IsElement item);

    void clear();

    void show(IsElement relativeTo, boolean b, String orientation);

    void addMouseEnterHandler(MouseEnterHandler mouseEnterHandler);

    void addMouseLeaveHandler(MouseLeaveHandler mouseLeaveHandler);
}
