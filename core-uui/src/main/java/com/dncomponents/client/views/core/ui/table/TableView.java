package com.dncomponents.client.views.core.ui.table;

import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.tree.TreeView;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface TableView extends TreeView {
    void setColumnWidth(int column, String width);

    void setHeaderColumnWidth(int column, String width);

    void clearHeaders();

    void addHeaderItem(Element element);

    default void addHeaderItem(IsElement headerCell) {
        addHeaderItem(headerCell.asElement());
    }

    void setHeaderBar(IsElement bar, int size);

    HTMLElement insertAfter(IsElement rowTable, int size);

    HTMLElement addItemToRowColSpan(IsElement toAdd, int colSize);

    void setPager(IsElement pager);

    void addFooterItem(IsElement element);

    void clearFooter();

    void setFooterColumnWidth(int j, String columnWidth);

    HTMLElement getHeaderRow();

    HTMLElement getFooterRow();

    void initFilteringHeader();
}