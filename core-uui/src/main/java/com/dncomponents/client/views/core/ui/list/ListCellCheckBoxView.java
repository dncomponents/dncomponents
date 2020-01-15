package com.dncomponents.client.views.core.ui.list;

import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.google.gwt.user.client.ui.HasValue;

/**
 * @author nikolasavic
 */
public interface ListCellCheckBoxView extends BaseCellView {
    HasValue<Boolean> getCheckbox();
}

