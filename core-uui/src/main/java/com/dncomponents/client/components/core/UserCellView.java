package com.dncomponents.client.components.core;


import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public class UserCellView {

    public HTMLElement rootPanel;

    public HTMLElement valuePanel;

    public UserCellView(HTMLElement rootPanel, HTMLElement valuePanel) {
        this.rootPanel = rootPanel;
        this.valuePanel = valuePanel;
    }
}
