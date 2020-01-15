package com.dncomponents.client.views.core.ui.table;

import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import elemental2.dom.Element;

/**
 * @author nikolasavic
 */
public interface TableRowView extends BaseCellView {

    void addRow(Element widget, int columnSize);

    Element addColumn();

    void addColumn(Element element);

    default void addColumn(IsElement element) {
        addColumn(element.asElement());
    }

    void clearCells();
}
