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
import com.dncomponents.client.components.core.selectionmodel.AbstractMultiSelectionGroup;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
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

public class CheckBoxSelectionGroup<T> extends AbstractMultiSelectionGroup<T, CheckBox<T>> implements HasIsElement {

    private static int id = 0;
    private String groupName;
    HTMLElement elementToWrap;

    public CheckBoxSelectionGroup() {
        setSelectionMode(DefaultMultiSelectionModel.SelectionMode.MULTI);
        setGroupName("checkboxGroup-" + id++);
    }

    public CheckBoxSelectionGroup(String groupName) {
        setSelectionMode(DefaultMultiSelectionModel.SelectionMode.MULTI);
        setGroupName(groupName);
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public void addItem(CheckBox<T> value) {
        super.addItem(value);
        value.setGroup(this);
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

    public void addEntityItems(List<T> items) {
        items.forEach(t -> addItem(new CheckBox<T>(t, this)));
    }

    public void addEntityItems(List<T> items, MainRenderer<T> defaultRenderer) {
        setDefaultRenderer(defaultRenderer);
        addEntityItems(items);
    }

    @Override
    public void setSelectedInView(CheckBox<T> model, boolean b) {
        model.setValue(b);
    }

    // ui binder field
    protected String uiField;

    MainRenderer<T> defaultRenderer = new MainRendererImpl<>();

    public void setDefaultRenderer(MainRenderer<T> defaultRenderer) {
        this.defaultRenderer = defaultRenderer;
    }

    public MainRenderer<T> getDefaultRenderer() {
        return defaultRenderer;
    }

    public static class CheckBoxSelectionGroupHtmlParser extends AbstractPluginHelper implements HtmlParser<CheckBoxSelectionGroup> {

        private static CheckBoxSelectionGroupHtmlParser instance;

        private CheckBoxSelectionGroupHtmlParser() {
        }

        public static CheckBoxSelectionGroupHtmlParser getInstance() {
            if (instance == null)
                return instance = new CheckBoxSelectionGroupHtmlParser();
            return instance;
        }

        @Override
        public CheckBoxSelectionGroup parse(Element htmlElement, Map<String, ?> elements) {
            String uiField = htmlElement.getAttribute(TemplateParser.KEY);
            CheckBoxSelectionGroup checkBoxSelectionGroup = new CheckBoxSelectionGroup();
            checkBoxSelectionGroup.uiField = uiField;
            elements.values().forEach((Consumer<Object>) o -> {
                if (o instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) o;
                    if (checkBox.groupUiField != null && checkBox.groupUiField.equals(uiField)) {
                        checkBoxSelectionGroup.addItem((CheckBox) o);
                    }
                }
            });
            NodeList<Element> elements2 = htmlElement.querySelectorAll("*");
            for (int i = 0; i < elements2.length; i++) {
                Element at = elements2.getAt(i);
                Object o = BaseComponent.allComponents.get(at);
                if (o instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) o;
                    if (DomUtil.isDescendant(htmlElement, checkBox.asElement())) {
                        checkBoxSelectionGroup.addItem(((CheckBox) o));
                    }
                }
            }
            DomUtil.unwrap(htmlElement);
            return checkBoxSelectionGroup;
        }

        @Override
        public String getId() {
            return "dn-checkbox-selection-group";
        }

        @Override
        public Class getClazz() {
            return CheckBoxSelectionGroup.class;
        }

    }

}
