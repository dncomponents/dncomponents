package com.dncomponents.client.views.core.ui.pager;

import com.dncomponents.client.views.core.pcg.View;

/**
 * @author nikolasavic
 */
public interface PagerItemView extends View {
    void setText(String str);

    void setActive(boolean b);
}
