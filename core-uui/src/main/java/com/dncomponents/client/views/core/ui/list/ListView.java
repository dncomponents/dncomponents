package com.dncomponents.client.views.core.ui.list;

import com.dncomponents.client.components.list.HasNavigationHandler;
import com.dncomponents.client.views.core.pcg.View;

/**
 * @author nikolasavic
 */
public interface ListView extends View, ScrollView, HasNavigationHandler {
    void setScrollable(boolean b);

    void setScrollHeight(String height);
}
