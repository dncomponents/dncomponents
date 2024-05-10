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

package com.dncomponents.material.client.table;

import com.dncomponents.Template;
import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ScrollHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.table.TableView;
import com.dncomponents.material.client.list.ListViewImpl;
import elemental2.dom.*;


@Template
public class TableViewImpl extends ListViewImpl implements TableView {

    @UiField
    HTMLTableColElement contentColgroupPanel;
    @UiField
    HTMLTableSectionElement rowsPanel;
    @UiField
    HTMLTableColElement headerColgroupPanel;
    @UiField
    HTMLTableRowElement headerRow;
    @UiField
    HTMLTableSectionElement headerBodyPanel;
    @UiField
    HTMLTableSectionElement footerBodyPanel;
    @UiField
    HTMLElement tableContent;
    @UiField
    public HTMLElement tableContentPanel;
    @UiField
    HTMLElement tableHeader;
    @UiField
    HTMLElement headerBarRow;

    //footer
    @UiField
    HTMLElement footerPanel;
    @UiField
    HTMLElement tableFooter;
    @UiField
    HTMLElement tableFooterColGroup;
    @UiField
    HTMLElement tableFooterRow;
    @UiField
    HTMLElement footerPagerPanel;

    HtmlBinder uiBinder = HtmlBinder.create(TableViewImpl.class, this);


    public TableViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }


    public TableViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public HTMLElement createEmptyRow() {
        return DomUtil.createTr();
    }

    @Override
    public void addHeaderItem(Element element) {
        headerRow.appendChild(element);
        headerColgroupPanel.appendChild(DomUtil.createCol());
        contentColgroupPanel.appendChild(DomUtil.createCol());
    }

    @Override
    public void clearHeaders() {
        headerRow.innerHTML = "";
        headerColgroupPanel.innerHTML = "";
        contentColgroupPanel.innerHTML = "";
    }

    @Override
    public void setColumnWidth(int column, String width) {
        ((HTMLElement) contentColgroupPanel
                .childNodes
                .getAt(column))
                .style.width = CSSProperties.WidthUnionType.of(width);
    }

    @Override
    public void setHeaderColumnWidth(int column, String width) {
        ((HTMLElement) headerColgroupPanel
                .childNodes
                .item(column))
                .style.width = CSSProperties.WidthUnionType.of(width);
    }

    @Override
    public void setScrollable(boolean b) {
        if (b)
            tableContentPanel.classList.add(scrollableStyle);
        else
            tableContentPanel.classList.remove(scrollableStyle);
    }

    @Override
    public HandlerRegistration addScrollHandler(ScrollHandler scrollHandler) {
        return scrollHandler.addTo(tableContentPanel);
    }

    @Override
    public double getScrollTop() {
        return tableContentPanel.scrollTop;
    }

    @Override
    public HTMLElement insertAfter(IsElement rowTable, int size) {
        HTMLElement tr = addToRow(size);
        if (rowTable != null && rowTable.asElement().parentNode != null)
            rowTable.asElement().parentNode.insertBefore(tr, rowTable.asElement().nextSibling);
        return (HTMLElement) tr.firstChild;
    }

    @Override
    public HTMLElement addItemToRowColSpan(IsElement toAdd, int colSize) {
        HTMLElement row = addToRow(colSize);
        addItem(row);
        return row;
    }

    private HTMLElement addToRow(int colSize) {
        HTMLElement row = DomUtil.createTr();
        HTMLElement column = DomUtil.createTd();
        column.setAttribute("colspan", colSize);
        row.appendChild(column);
        return row;
    }

    @Override
    public void setPager(IsElement pager) {
        footerPagerPanel.innerHTML = "";
        footerPagerPanel.appendChild(pager.asElement());
    }

    @Override
    public void addFooterItem(IsElement element) {
        tableFooterColGroup.appendChild(DomUtil.createCol());
        tableFooterRow.appendChild(element == null ? DomUtil.createTd() : element.asElement());
    }


    @Override
    public void clearFooter() {
        tableFooterRow.innerHTML = "";
        tableFooterColGroup.innerHTML = "";
    }

    @Override
    public void setFooterColumnWidth(int column, String width) {
        if (tableFooterColGroup.hasChildNodes())
            ((HTMLElement) tableFooterColGroup
                    .childNodes
                    .item(column))
                    .style.width = CSSProperties.WidthUnionType.of(width);
    }

    @Override
    public HTMLElement getHeaderRow() {
        return headerBodyPanel;
    }

    @Override
    public HTMLElement getFooterRow() {
        return footerBodyPanel;
    }

    @Override
    public void initFilteringHeader() {

    }

    @Override
    protected HTMLElement getItemPanel() {
        return rowsPanel;
    }
}
