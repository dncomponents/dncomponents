package com.dncomponents.client.views.core.ui.table.headers.bar;

import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface TableBarView extends View {
    void add(HTMLElement element);

    default void add(IsElement isElement) {
        add(isElement.asElement());
    }
}
