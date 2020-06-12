package com.dncomponents.client.views.core.ui.dropdown;

import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface DropDownItemView extends View, MainViewSlots.HasMainViewSlots {

    void setContent(String content);

    void setHtmlContent(HTMLElement content);

    void addClickHandler(ClickHandler clickHandler);

    void setActive(boolean active);
}