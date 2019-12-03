package com.dncomponents.client.views.core.ui.button;

import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.ViewSlots;
import elemental2.dom.HTMLElement;

public interface ButtonViewSlots extends ViewSlots {
    interface HasButtonViewSlots extends HasViewSlots<ButtonViewSlots> {
    }

    HTMLElement getMainSlot();
}