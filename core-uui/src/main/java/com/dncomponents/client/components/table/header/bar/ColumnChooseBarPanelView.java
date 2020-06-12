package com.dncomponents.client.components.table.header.bar;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.HasRowsDataList;
import com.dncomponents.client.components.core.events.open.HasOpenHandlers;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;

public interface ColumnChooseBarPanelView extends View {

    IsElement getLabel();

    DefaultMultiSelectionModel getSelectionModel();

    HasRowsDataList<ColumnConfig> getHasRows();

    HasOpenHandlers getPopupShowHandler();

    void update(int diff);
}
