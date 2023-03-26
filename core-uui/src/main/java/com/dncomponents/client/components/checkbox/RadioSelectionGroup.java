/*
 * Copyright 2023 dncomponents
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

package com.dncomponents.client.components.checkbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.HtmlParser;
import com.dncomponents.client.components.core.TemplateParser;
import com.dncomponents.client.components.core.selectionmodel.AbstractSingleSelectionGroup;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.MainRendererImpl;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RadioSelectionGroup<T> extends AbstractSingleSelectionGroup<T, Radio<T>> implements HasIsElement{

    private static int id = 0;
    private String groupName;
    HTMLElement elementToWrap;

    public RadioSelectionGroup() {
        setGroupName("radioGroup-" + id++);
    }

    public RadioSelectionGroup(String groupName) {
        setGroupName(groupName);
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void addEntityItems(List<T> items) {
        items.forEach(t -> addItem(new Radio<T>(t, this)));
    }

    public void addEntityItems(List<T> items, MainRenderer<T> defaultRenderer) {
        setDefaultRenderer(defaultRenderer);
        addEntityItems(items);
    }

    MainRenderer<T> defaultRenderer = new MainRendererImpl<>();

    public MainRenderer<T> getDefaultRenderer() {
        return defaultRenderer;
    }

    public void setDefaultRenderer(MainRenderer<T> defaultRenderer) {
        this.defaultRenderer = defaultRenderer;
    }

    @Override
    public void setSelectedInView(Radio<T> model, boolean b) {
        model.setValue(b);
    }

    @Override
    public void addItem(Radio<T> value) {
        super.addItem(value);
        value.setGroup(this);
        if (value.isValueTrue()) {
            setSelected(value, true);
        }
    }
    public void setElementToWrap(HTMLElement elementToWrap) {
        this.elementToWrap = elementToWrap;
    }

    public HTMLElement getElementToWrap() {
        return elementToWrap;
    }

    @Override
    public IsElement asIsElement() {
        if (elementToWrap == null)
            elementToWrap = DomUtil.createDiv();
        getItems().forEach(e -> elementToWrap.appendChild(e.asElement()));
        return () -> elementToWrap;
    }
    // ui binder field
    protected String uiField;

    public static class RadioSelectionGroupHtmlParser extends AbstractPluginHelper implements HtmlParser<RadioSelectionGroup> {

        private static RadioSelectionGroupHtmlParser instance;

        private RadioSelectionGroupHtmlParser() {
        }

        public static RadioSelectionGroupHtmlParser getInstance() {
            if (instance == null)
                return instance = new RadioSelectionGroupHtmlParser();
            return instance;
        }

        @Override
        public RadioSelectionGroup parse(Element htmlElement, Map<String, ?> elements) {
            String uiField = htmlElement.getAttribute(TemplateParser.KEY);
            RadioSelectionGroup radioSelectionGroup = new RadioSelectionGroup();
            radioSelectionGroup.uiField = uiField;
            elements.values().forEach((Consumer<Object>) o -> {
                if (o instanceof Radio) {
                    Radio radio = (Radio) o;
                    if (radio.groupUiField != null && radio.groupUiField.equals(uiField)) {
                        radioSelectionGroup.addItem((Radio) o);
                    }
                }
            });
            NodeList<Element> elements2 = htmlElement.querySelectorAll("*");
            for (int i = 0; i < elements2.length; i++) {
                Element at = elements2.getAt(i);
                Object o = BaseComponent.allComponents.get(at);
                if (o instanceof Radio) {
                    Radio radio = (Radio) o;
                    if (DomUtil.isDescendant(htmlElement, radio.asElement())) {
                        radioSelectionGroup.addItem(((Radio) o));
                    }
                }
            }
            DomUtil.unwrap(htmlElement);
            return radioSelectionGroup;
        }

        @Override
        public String getId() {
            return "dn-radio-selection-group";
        }

        @Override
        public Class getClazz() {
            return RadioSelectionGroup.class;
        }
    }
}
