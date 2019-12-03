package com.dncomponents.client.views.core.ui.checkbox;

import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.ViewSlots;
import elemental2.dom.HTMLElement;

public interface CheckBoxViewSlots extends ViewSlots {
    interface HasGCheckBoxViewSlots extends HasViewSlots<CheckBoxViewSlots> {
    }

    HTMLElement getMainSlot();
}
