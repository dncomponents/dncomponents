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

package com.dncomponents.client.components.core;

import elemental2.dom.Element;

import java.util.Map;

public class ValueHtmlParser extends AbstractPluginHelper implements HtmlParser<String> {

    private static ValueHtmlParser instance;

    private ValueHtmlParser() {
    }

    public static ValueHtmlParser getInstance() {
        if (instance == null)
            return instance = new ValueHtmlParser();
        return instance;
    }


    @Override
    public String parse(Element htmlElement, Map<String, ?> template) {
        try {
            if (htmlElement.hasAttribute("value")) {
                return htmlElement.getAttribute("value");
            } else {
                return htmlElement.textContent;
            }
        } finally {
            htmlElement.remove();
        }
    }

    @Override
    public String getId() {
        return "value-item";
    }

    @Override
    public Class getClazz() {
        return String.class;
    }

}

