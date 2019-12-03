package com.dncomponents.client.views.core.ui.accordion;

import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;

/**
 * @author nikolasavic
 */
public interface AccordionView extends View {
    void addItem(IsElement item);

    void removeItem(IsElement item);

    void clearAll();
}
