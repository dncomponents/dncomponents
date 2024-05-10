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

package com.dncomponents.material.client.table.header.filter;

import com.dncomponents.UiField;
import com.dncomponents.client.components.HasRowsDataList;
import com.dncomponents.client.components.autocomplete.Autocomplete;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.CellEditor;
import com.dncomponents.client.components.core.DefaultCellEditor;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.TemplateParser;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.table.header.filter.Comparator;
import com.dncomponents.client.components.textbox.*;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.table.headers.FilterPanelView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

import java.util.Date;


public class FilterPanelViewImpl<T> implements FilterPanelView<T> {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement clear;
    @UiField
    Autocomplete<Comparator> ac;
    @UiField
    public HTMLElement valueHolder;
    HTMLTemplateElement templateElement;
    TemplateParser templateParser = new TemplateParser(templateElement);

    {
        root = templateParser.getHTMLElement("root");
        clear = templateParser.getHTMLElement("clear");
        valueHolder = templateParser.getHTMLElement("valueHolder");
        ac = templateParser.getElement("ac");
    }


    CellEditor<T> cellEditor;

    HtmlBinder uiBinder = HtmlBinder.create(FilterPanelViewImpl.class, this);
    @UiField
    public HTMLElement mainPanel;
    @UiField
    public String booleanMainPanelStyle;

    public FilterPanelViewImpl() {
        init();
    }

    public FilterPanelViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        init();
    }

    private void init() {
        uiBinder.bind();
        DomUtil.setVisible(clear, false);
    }

    @Override
    public void addClearClickHandler(ClickHandler clickHandler) {
        clickHandler.addTo(clear);
    }

    @Override
    public void showClearElement(boolean b) {
        DomUtil.setVisible(clear, b);
    }

    @Override
    public CellEditor<T> getValueComponent() {
        return cellEditor;
    }

    @Override
    public void setValueComponent(CellEditor<T> valueWidget) {
        if (valueWidget != null) {
            DomUtil.replaceRaw(valueWidget.getIsElement(), valueHolder);
            this.cellEditor = valueWidget;
            valueHolder = cellEditor.getIsElement().asElement();
        } else {
            HTMLElement holder = DomUtil.createDiv();
            DomUtil.replaceRaw(holder, valueHolder);
            valueHolder = holder;
        }
    }

    @Override
    public <T1> void setValueComponent(Class<T1> clzz) {
        if (clzz == null)
            throw new NullPointerException("Class argument can't be null!");
        if (clzz == Integer.class) {
            cellEditor = new DefaultCellEditor(new IntegerBox());
        } else if (clzz == String.class) {
            cellEditor = new DefaultCellEditor(new TextBox());
        } else if (clzz == Double.class) {
            cellEditor = new DefaultCellEditor(new DoubleBox());
        } else if (clzz == Boolean.class) {
            mainPanel.className = booleanMainPanelStyle;
            mainPanel.appendChild(ac.asElement());
            cellEditor = new DefaultCellEditor(new CheckBox());
        } else if (clzz == Long.class) {
            cellEditor = new DefaultCellEditor(new LongBox());
        } else if (clzz == Date.class) {
            cellEditor = new DefaultCellEditor(new DateBox());
        } else
            throw new IllegalArgumentException(clzz + " has no defined editor!");
    }

    @Override
    public HasValue<Comparator> getComparatorHasValue() {
        return ac;
    }

    @Override
    public HasRowsDataList<Comparator> getComparatorHasRowsData() {
        return ac.getHasRowsDataList();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

}
