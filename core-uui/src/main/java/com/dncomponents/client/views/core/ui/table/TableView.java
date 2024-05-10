/*
 * Copyright 2024 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.client.views.core.ui.table;

import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.tree.TreeView;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;


public interface TableView extends TreeView {

    void setColumnWidth(int column, String width);

    void setHeaderColumnWidth(int column, String width);

    void clearHeaders();

    void addHeaderItem(Element element);

    default void addHeaderItem(IsElement headerCell) {
        addHeaderItem(headerCell.asElement());
    }

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
