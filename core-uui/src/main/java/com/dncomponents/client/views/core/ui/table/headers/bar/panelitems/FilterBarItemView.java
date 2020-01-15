package com.dncomponents.client.views.core.ui.table.headers.bar.panelitems;

import com.dncomponents.client.components.table.header.filter.HasFilterValue;
import com.google.gwt.user.client.ui.HasValue;

/**
 * @author nikolasavic
 */
public interface FilterBarItemView extends BarItemView {

    void setFilterComponent(HasFilterValue element);

    HasFilterValue getFilterComponent();

    HasValue<Boolean> getOrHandler();
}
