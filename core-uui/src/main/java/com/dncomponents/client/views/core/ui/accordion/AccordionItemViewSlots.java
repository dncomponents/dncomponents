package com.dncomponents.client.views.core.ui.accordion;

import com.dncomponents.client.views.ViewSlots;
import elemental2.dom.HTMLElement;

public interface AccordionItemViewSlots extends ViewSlots {
    HTMLElement getTitle();

    HTMLElement getContent();
}
