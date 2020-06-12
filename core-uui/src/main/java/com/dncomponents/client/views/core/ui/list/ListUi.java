package com.dncomponents.client.views.core.ui.list;

import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;

/**
 * @author nikolasavic
 */
public interface ListUi extends ComponentUi<ListView> {

    BaseCellView getListCellView();

    ListCellCheckBoxView getListCheckBoxView();
}
