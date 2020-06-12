package com.dncomponents.client.views;

import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public class MainViewSlotsImpl implements MainViewSlots {
    HTMLElement mainPanel;

    public MainViewSlotsImpl(HTMLElement mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public HTMLElement getMainSlot() {
        return mainPanel;
    }
}
