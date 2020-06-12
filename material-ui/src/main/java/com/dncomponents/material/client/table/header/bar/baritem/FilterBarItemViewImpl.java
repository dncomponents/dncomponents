package com.dncomponents.material.client.table.header.bar.baritem;

import com.dncomponents.UiField;
import com.dncomponents.client.components.autocomplete.Autocomplete;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.table.header.filter.HasFilterValue;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.table.headers.bar.panelitems.FilterBarItemView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

import java.util.ArrayList;

/**
 * @author nikolasavic
 */
public class FilterBarItemViewImpl implements FilterBarItemView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement close;
    @UiField
    HTMLElement actionLabel;
    @UiField
    HTMLElement columnName;
    @UiField
    HTMLElement panel;

    HtmlBinder uiBinder = HtmlBinder.get(FilterBarItemViewImpl.class, this);

    public FilterBarItemViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        setActionAndOr();
    }

    public FilterBarItemViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        setActionAndOr();

    }


    HasFilterValue filterComponent;
    Autocomplete<Boolean> orAc = new Autocomplete<>(b -> b ? "OR" : "AND");

    {
        ArrayList<Boolean> lists = new ArrayList<>();
        lists.add(true);
        lists.add(false);
        orAc.setRowsData(lists);
        orAc.setValue(true);

    }

    @Override
    public void addToPanel(IsElement element) {
        HTMLElement column = DomUtil.createDiv();
        column.className = "col";
        column.appendChild(element.asElement());
        panel.appendChild(column);
    }

    @Override
    public void addDeleteHandler(ClickHandler clickHandler) {
        clickHandler.addTo(close);
    }

    @Override
    public void setActionLabel(String text) {
        actionLabel.innerHTML = "";
        actionLabel.innerHTML = "<b>" + text + "</b>";
    }

    public void setActionAndOr() {
        actionLabel.innerHTML = "";
        actionLabel.appendChild(orAc.asElement());
    }

    @Override
    public void setColumnName(String name) {
        columnName.innerHTML = "<b>" + name + "</b>";
    }

    @Override
    public void clear() {
        panel.innerHTML = "";
    }


    @Override
    public void setFilterComponent(HasFilterValue element) {
        filterComponent = element;
        addToPanel(filterComponent);
    }

    @Override
    public HasFilterValue getFilterComponent() {
        return filterComponent;
    }

    @Override
    public HasValue<Boolean> getOrHandler() {
        return orAc;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}