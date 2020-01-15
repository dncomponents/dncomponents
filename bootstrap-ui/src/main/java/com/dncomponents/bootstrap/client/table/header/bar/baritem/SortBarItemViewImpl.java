package com.dncomponents.bootstrap.client.table.header.bar.baritem;

import com.dncomponents.UiField;
import com.dncomponents.client.components.checkbox.Radio;
import com.dncomponents.client.components.checkbox.RadioSelectionGroup;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.header.SortingDirection;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.table.headers.bar.panelitems.SortBarItemView;
import com.google.gwt.event.logical.shared.SelectionHandler;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class SortBarItemViewImpl implements SortBarItemView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement close;
    @UiField("action-label")
    HTMLElement actionLabel;
    @UiField("column-name")
    HTMLElement columnName;
    @UiField
    HTMLElement panel;
    @UiField
    HtmlBinder uiBinder = HtmlBinder.get(SortBarItemViewImpl.class, this);

    public SortBarItemViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }

    public SortBarItemViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }


    RadioSelectionGroup<SortingDirection> group = new RadioSelectionGroup<>();
    Radio asc = new Radio(SortingDirection.ASCENDING);
    Radio desc = new Radio(SortingDirection.DESCENDING);

    private void init() {
        group.addItems(asc, desc);
        addToPanel(asc);
        addToPanel(desc);
    }

    @Override
    public void addToPanel(IsElement element) {
        HTMLElement column = DomUtil.createDiv();
        column.className = "mr-2";
        column.appendChild(element.asElement());
        panel.appendChild(column);
    }

    @Override
    public void addDeleteHandler(ClickHandler clickHandler) {
        clickHandler.addTo(close);
    }

    @Override
    public void setActionLabel(String text) {
        actionLabel.innerHTML = "<b>" + text + "</b>";
    }

    @Override
    public void setActionAndOr() {

    }

    @Override
    public void setColumnName(String name) {
        columnName.innerHTML = "<b>" + name + "</b>";
    }

    @Override
    public void addSelectionHandler(SelectionHandler<SortingDirection> handler) {
        group.getEntitySelectionModel().addSelectionHandler(handler);
    }

    @Override
    public void setDirection(SortingDirection direction) {
        asc.setValue(direction == SortingDirection.ASCENDING);
        desc.setValue(direction == SortingDirection.DESCENDING);
    }

    @Override
    public void clear() {
        panel.innerHTML = "";
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
