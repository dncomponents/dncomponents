package com.dncomponents.client.views.core.ui.dropdown;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;
import com.google.gwt.user.client.Command;
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

    void addClickOutOfButton(Command command);

    void addDropDownPanel(IsElement dropDownPanel);
}
