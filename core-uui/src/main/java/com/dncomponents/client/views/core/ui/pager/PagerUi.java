package com.dncomponents.client.views.core.ui.pager;

import com.dncomponents.client.views.core.pcg.ComponentUi;

/**
 * @author nikolasavic
 */
public interface PagerUi<V extends PagerView> extends ComponentUi<V> {
    PagerItemView getPagerItemView();
}
