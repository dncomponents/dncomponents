package com.dncomponents.client.views.core.ui.table.headers.bar.panel;

import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.components.table.header.HeaderGrouping;

/**
 * @author nikolasavic
 */
public interface GroupByBarPanelView extends BaseBarPanelView<HeaderGrouping> {
    void addExpandAllHandler(ValueChangeHandler<Boolean> handler);

    interface Presenter extends BaseBarPanelView.Presenter<HeaderGrouping> {
        void expandAll(boolean b);
    }
}
