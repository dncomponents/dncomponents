package com.dncomponents.material.client.table;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ScrollHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.table.TableView;
import com.dncomponents.material.client.list.MdcListViewImpl;
import com.google.gwt.event.shared.HandlerRegistration;
import elemental2.dom.*;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TableViewImpl extends MdcListViewImpl implements TableView {

    @UiField("table-content-colgroup")
    HTMLTableColElement contentColgroupPanel;
    @UiField("table-content-tbody")
    HTMLTableSectionElement rowsPanel;
    @UiField("table-header-colgroup")
    HTMLTableColElement headerColgroupPanel;
    @UiField("table-header-row")
    HTMLTableRowElement headerRow;
    @UiField
    HTMLTableSectionElement headerBodyPanel;
    @UiField
    HTMLTableSectionElement footerBodyPanel;
    @UiField("table-content")
    HTMLElement tableContent;
    @UiField("table-content-panel")
    public HTMLElement tableContentPanel;
    @UiField("table-header")
    HTMLElement tableHeader;
    @UiField("table-header-bar-row")
    HTMLElement headerBarRow;

    //footer
    @UiField("table-footer-panel")
    HTMLElement footerPanel;
    @UiField("table-footer")
    HTMLElement tableFooter;
    @UiField("table-footer-colgroup")
    HTMLElement tableFooterColGroup;
    @UiField("table-footer-row")
    HTMLElement tableFooterRow;
    @UiField("footer-pager-panel")
    HTMLElement footerPagerPanel;

    HtmlBinder uiBinder = HtmlBinder.get(TableViewImpl.class, this);


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
    public void setHeaderBar(IsElement bar, int size) {
        headerBarRow.innerHTML = "";
        headerBarRow.appendChild(bar.asElement());
        headerBarRow.style.display = "block";
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