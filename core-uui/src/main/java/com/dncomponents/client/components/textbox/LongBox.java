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


public class LongBox extends ValueBox<Long> {

    public LongBox() {
        super(Ui.get().getTextBoxView());
    }

    public LongBox(TextBoxView view) {
        super(view);
    }

    @Override
    Long parseString(String str) throws ValidationException {
        Long result;
        try {
            result = Long.parseLong(str);
        } catch (NumberFormatException ex) {
            throw new ValidationException(ex.getMessage());
        }
        return result;
    }

    @Override
    String render(Long value) {
        return value == null ? "" : value.toString();
    }

    public static class LongBoxHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {


        private static LongBoxHtmlParser instance;

        private LongBoxHtmlParser() {
        }

        public static LongBoxHtmlParser getInstance() {
            if (instance == null)
                return instance = new LongBoxHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            LongBox longBox;
            TextBoxView view = getView(TextBox.class, htmlElement, elements);
            if (view != null)
                longBox = new LongBox(view);
            else
                longBox = new LongBox();
            String value = htmlElement.getAttribute(VALUE);
            if (value != null) {
                try {
                    longBox.setValue(Long.parseLong(value));
                } catch (Exception ex) {
                    DomGlobal.console.warn("Warning: error parsing long value: " + value);
                }
            }
            replaceAndCopy(htmlElement, longBox);
            return longBox;
        }

        @Override
        public String getId() {
            return "dn-long-box";
        }

        @Override
        public Class getClazz() {
            return LongBox.class;
        }
    }

}
