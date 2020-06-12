package com.dncomponents.material.client.table;

import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.table.TableRowView;
import com.dncomponents.material.client.cell.BaseCellViewImpl;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TableRowViewImpl extends BaseCellViewImpl implements TableRowView {

    HtmlBinder uiBinder = HtmlBinder.get(TableRowViewImpl.class, this);

    public TableRowViewImpl() {
        uiBinder.setTemplateElement(((TableUiImpl) (Ui.get()).getTableUi()).tableRow);
        uiBinder.bind();
    }

    public TableRowViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }


    public TableRowViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public Element addColumn() {
        return null;
    }

    @Override
    public void addRow(Element widget, int columnSize) {
    }

    @Override
    public void addColumn(Element element) {
        asElement().appendChild(element);
    }

    @Override
    public void clearCells() {
        asElement().innerHTML = "";
    }

    @Override
    public HTMLElement getValuePanel() {
        return asElement();
    }
}