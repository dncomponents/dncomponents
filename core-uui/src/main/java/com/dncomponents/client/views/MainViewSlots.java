package com.dncomponents.client.views;

import elemental2.dom.HTMLElement;

public interface MainViewSlots extends ViewSlots {
    interface HasMainViewSlots extends HasViewSlots<MainViewSlots> {
    }

    HTMLElement getMainSlot();
}
