package com.dncomponents.client.views.core.ui.list;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.handlers.ScrollHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface ScrollView extends View { //HasScrollHandlers

    int getRowHeight();

    void addItem(Element element);

    void addItemAtTop(Element element);

    default void addItemAtTop(IsElement element) {
        addItemAtTop(element.asElement());
    }

    default void addItem(IsElement element) {
        addItem(element.asElement());
    }

    void clear();

    HandlerRegistration addScrollHandler(ScrollHandler scrollHandler);

    double getScrollTop();

    HTMLElement createEmptyRow();

    void resetScrollTop(Double value);

    HTMLElement getScrollPanel();

    void setScrollHeight(String height);

}
