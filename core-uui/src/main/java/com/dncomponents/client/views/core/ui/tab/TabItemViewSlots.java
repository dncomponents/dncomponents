package com.dncomponents.client.views.core.ui.tab;

import com.dncomponents.client.views.ViewSlots;
import elemental2.dom.HTMLElement;

public interface TabItemViewSlots extends ViewSlots {
    HTMLElement getTitle();

    HTMLElement getContent();
}
