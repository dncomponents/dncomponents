package com.dncomponents.client.views.core.ui.accordion;

import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface AccordionItemView extends View, HasViewSlots<AccordionItemViewSlots> {

    void addItemSelectedHandler(EventListener handler);

    void select(boolean b);

    void setItemTitle(String html);

    void setItemTitle(HTMLElement html);

    void setItemContent(String html);

    void setItemContent(HTMLElement htmlElement);

    void setImmediate(Command command);

    String getTitle();

    String getContent();
}