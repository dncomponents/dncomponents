package com.dncomponents.client.views.core.ui.dropdown;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface DropDownView extends View {
    void addClickOnButton(BaseEventListener handler);

    void showList(boolean b);

    void addItem(IsElement item);

    void removeItem(IsElement item);

    void setButtonHtmlContent(HTMLElement content);

    IsElement getRelativeElement();

    void setButtonContent(String content);

    HandlerRegistration addClickOutOfButton(ClickHandler clickHandler);

    void addDropDownPanel(IsElement dropDownPanel);
}
