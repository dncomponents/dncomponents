package com.dncomponents.client.views.core.ui.tab;

import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface TabView extends View {
    void  addItem(HTMLElement tabItem, HTMLElement tabContent);

    void removeItem(HTMLElement tabItem, HTMLElement tabContent);
}
