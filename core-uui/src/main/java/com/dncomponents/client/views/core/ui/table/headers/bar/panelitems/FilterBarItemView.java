package com.dncomponents.client.views.core.ui.table.headers.bar.panelitems;

import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.table.header.filter.HasFilterValue;

/**
 * @author nikolasavic
 */
public interface FilterBarItemView extends BarItemView {

    void setFilterComponent(HasFilterValue element);

    HasFilterValue getFilterComponent();

    HasValue<Boolean> getOrHandler();
}
