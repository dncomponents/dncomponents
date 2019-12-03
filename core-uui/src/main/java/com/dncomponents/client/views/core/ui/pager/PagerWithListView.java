package com.dncomponents.client.views.core.ui.pager;

import com.dncomponents.client.views.IsElement;

import java.util.ArrayList;

/**
 * @author nikolasavic
 */
public interface PagerWithListView extends PagerView {

    void setNumberOfPages(int numberOfPages);

    void update(ArrayList<Integer> itemsList);

    void addItem(IsElement element);

    void clear();
}
