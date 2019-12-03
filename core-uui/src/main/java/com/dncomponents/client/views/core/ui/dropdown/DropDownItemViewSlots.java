package com.dncomponents.client.views.core.ui.dropdown;

import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.ViewSlots;
import elemental2.dom.HTMLElement;

public interface DropDownItemViewSlots extends ViewSlots {
    interface HasDropDownItemViewSlots extends HasViewSlots<DropDownItemViewSlots> {
    }

    HTMLElement getMainSlot();
}
