package com.dncomponents.client.components.table.columnclasses.editcolumn;

import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;

/**
 * @author nikolasavic
 */
public interface TableCellEditView extends BaseCellView {
    void addOnEditHandler(ClickHandler c);

    void addOnCancelHandler(ClickHandler c);

    void addOnSaveHandler(ClickHandler c);

    void addOnDeleteHandler(ClickHandler c);

    void setEditMode(boolean b);
}
