package com.dncomponents.client.components.core;

import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;

public interface SelectionView extends View {
    void addItem(IsElement item);

    void removeItem(IsElement item);
}
