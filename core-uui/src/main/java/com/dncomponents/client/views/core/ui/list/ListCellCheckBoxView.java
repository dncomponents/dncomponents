package com.dncomponents.client.views.core.ui.list;

import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;

/**
 * @author nikolasavic
 */
public interface ListCellCheckBoxView extends BaseCellView {
    HasValue<Boolean> getCheckbox();
}

