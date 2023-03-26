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

package com.dncomponents.client.components.textbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import elemental2.dom.Element;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class TextBox extends ValueBox<String> {

    public TextBox() {
        super(Ui.get().getTextBoxView());
    }

    public TextBox(TextBoxView view) {
        super(view);
    }

    @Override
    String parseString(String str) {
        return str == "" ? null : str;
    }

    @Override
    String render(String s) {
        return s == null ? "" : s;
    }

    /**
     * Return the parsed value, or null if the field is empty or parsing fails.
     */
    public String getValueFromView() {
        String result;
        try {
            result = getValueOrThrow();
        } catch (Exception ex) {
            result = null;
        }
        return result;
    }

    public static class TextBoxHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static TextBoxHtmlParser instance;

        private TextBoxHtmlParser() {
        }

        public static TextBoxHtmlParser getInstance() {
            if (instance == null)
                return instance = new TextBoxHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            TextBox textBox;
            TextBoxView view = getView(TextBox.class, htmlElement, elements);
            if (view != null)
                textBox = new TextBox(view);
            else
                textBox = new TextBox();
            String value = htmlElement.getAttribute(VALUE);
            if (value != null) {
                textBox.setValue(value);
            }
            replaceAndCopy(htmlElement, textBox);
            return textBox;
        }

        @Override
        public String getId() {
            return "dn-text-box";
        }

        @Override
        public Class getClazz() {
            return TextBox.class;
        }
    }
}
