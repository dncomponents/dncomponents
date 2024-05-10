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

package com.dncomponents.client.components.textbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;

import java.util.Map;


public class DoubleBox extends ValueBox<Double> {

    public DoubleBox() {
        super(Ui.get().getTextBoxView());
    }

    public DoubleBox(TextBoxView view) {
        super(view);
    }

    @Override
    Double parseString(String str) throws ValidationException {
        Double result;
        try {
            result = Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            throw new ValidationException(ex.getMessage());
        }
        return result;
    }

    @Override
    String render(Double value) {
        return value == null ? "" : value.toString();
    }


    public static class DoubleBoxHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {


        private static DoubleBoxHtmlParser instance;

        private DoubleBoxHtmlParser() {
        }

        public static DoubleBoxHtmlParser getInstance() {
            if (instance == null)
                return instance = new DoubleBoxHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            DoubleBox doubleBox;
            TextBoxView view = getView(TextBox.class, htmlElement, elements);
            if (view != null)
                doubleBox = new DoubleBox(view);
            else
                doubleBox = new DoubleBox();

            String value = htmlElement.getAttribute(VALUE);
            if (value != null) {
                try {
                    doubleBox.setValue(Double.parseDouble(value));
                } catch (Exception ex) {
                    DomGlobal.console.warn("Warning: error parsing double value: " + value);
                }
            }

            replaceAndCopy(htmlElement, doubleBox);
            return doubleBox;
        }

        @Override
        public String getId() {
            return "dn-double-box";
        }

        @Override
        public Class getClazz() {
            return DoubleBox.class;
        }
    }

}
