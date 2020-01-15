package com.dncomponents.client.components.table.header.bar;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.components.HasRows;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.views.core.pcg.View;
import com.google.gwt.event.logical.shared.HasOpenHandlers;

public interface ColumnChooseBarPanelView extends View {

    IsElement getLabel();

    DefaultMultiSelectionModel getSelectionModel();

    HasRows<ColumnConfig> getHasRows();

    HasOpenHandlers getPopupShowHandler();

    void update(int diff);
}
