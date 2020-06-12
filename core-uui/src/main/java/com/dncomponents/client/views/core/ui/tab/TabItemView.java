package com.dncomponents.client.views.core.ui.tab;

import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface TabItemView extends View, HasViewSlots<TabItemViewSlots> {

    void addItemSelectedHandler(EventListener handler);

    void select(boolean b);

    void setItemTitle(String text);

    void setItemTitleHtml(String html);

    void setItemTitle(HTMLElement html);

    void setItemContent(String html);

    void setItemContent(HTMLElement htmlElement);

    void setImmediate(Command command);

    HTMLElement getTabItemNav();

    HTMLElement getTabItemContent();
}