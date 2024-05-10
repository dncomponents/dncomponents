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

package com.dncomponents.client.components.checkbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.dom.handlers.OnChangeHandler;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import elemental2.dom.Element;
import elemental2.dom.Event;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;


public class CheckBox<T> extends AbstractCheckBox<T> {

    protected CheckBoxSelectionGroup<T> group;

    Boolean value;

    public CheckBox(CheckBoxView view) {
        super(view);
        bind();
    }

    public CheckBox() {
        super(Ui.get().getCheckBoxView());
        bind();
    }

    public CheckBox(T userObject) {
        this();
        setUserObject(userObject);
        setLabel(userObject.toString());
    }

    public CheckBox(T userObject, CheckBoxSelectionGroup<T> group) {
        this();
        setGroup(group);
        setUserObject(userObject);
    }

    public CheckBox(String label) {
        this();
        setLabel(label);
    }

    protected void bind() {
        view.addOnChangeHandler(new OnChangeHandler() {
            @Override
            public void onChange(Event inputEvent) {
                if (!isEnabled())
                    return;
                fromView = true;
                setValue(view.isChecked(), true);
                if (group != null) {
                    group.setSelected(CheckBox.this, view.isChecked(), true);
                }
                fromView = false;
            }
        });
    }

    public void setIndeterminate(boolean b) {
        value = null;
        view.setIndeterminate(b);
    }

    public void setGroup(CheckBoxSelectionGroup<T> group) {
        this.group = group;
        view.setName(group.getGroupName());
        setRenderer(group.getDefaultRenderer());
    }

    //uibinder field
    protected String groupUiField;

    public static class CheckBoxHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static CheckBoxHtmlParser instance;
        //refer to ui-field of selection group
        public static final String GROUP = "group";
        public static final String VALUE = "value";
        public static final String DISABLED = "disabled";
//        public static final String LABEL = "label";

        private CheckBoxHtmlParser() {
            arguments.put(GROUP, Collections.emptyList());
            arguments.put(VALUE, Arrays.asList("true", "false"));
            arguments.put(DISABLED, Collections.emptyList());
//            arguments.put(LABEL, Collections.emptyList());
        }

        public static CheckBoxHtmlParser getInstance() {
            if (instance == null)
                return instance = new CheckBoxHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> templateElement) {
            CheckBoxView viewC = getView(CheckBox.class, htmlElement, templateElement);
            CheckBox<ItemId> checkBox;
            if (viewC != null)
                checkBox = new CheckBox(viewC);
            else
                checkBox = new CheckBox();

            setValueCh(htmlElement, checkBox);
            checkBox.setEnabled(!htmlElement.hasAttribute(DISABLED));
            checkBox.setRenderer(new MainRenderer<ItemId>() {
                @Override
                public void render(ItemId itemId, MainViewSlots slots) {
                    slots.getMainSlot().innerHTML = itemId.getContent();
                }
            });
            checkBox.setUserObject(getIdItem(htmlElement));
            checkBox.groupUiField = htmlElement.getAttribute(GROUP);
            replaceAndCopy(htmlElement, checkBox);
            return checkBox;
        }

        protected static void setValueCh(Element htmlElement, AbstractCheckBox checkBox) {
            String value = htmlElement.getAttribute(VALUE);
            if (value != null && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
                checkBox.setValue(value.equalsIgnoreCase("true") ? true : false);
            }
        }

        @Override
        public String getId() {
            return "dn-checkbox";
        }

        @Override
        public Class getClazz() {
            return CheckBox.class;
        }

    }

}
