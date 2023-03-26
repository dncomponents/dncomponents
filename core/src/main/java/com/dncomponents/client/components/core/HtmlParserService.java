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


import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class HtmlParserService {
    public static final List<IsElementHtmlParser> COMPONENT_HTML_PARSERS = new ArrayList<>();
    public static final List<HtmlParser> HTML_PARSERS = new ArrayList<>();
    public static final List<HtmlParser> HTML_PARSERS_BEFORE = new ArrayList<>();

    static {
        register();
    }

    public static void register() {
        HTML_PARSERS.add(StyleHtmlParser.getInstance());
        HTML_PARSERS.add(ValueHtmlParser.getInstance());
    }

    public static void registerComponent(IsElementHtmlParser parser) {
        COMPONENT_HTML_PARSERS.add(parser);
    }

    public static void register(HtmlParser parser) {
        HTML_PARSERS.add(parser);
    }

    public static IsElementHtmlParser<IsElement> getComponentParser(String tag) {
        return getParser(tag, COMPONENT_HTML_PARSERS);
    }

    public static HtmlParser getParser(String tag) {
        return getParser(tag, HTML_PARSERS);
    }

    public static boolean isComponentParserTag(Element element) {
        return isParserTag(element, COMPONENT_HTML_PARSERS);
    }

    public static boolean isParserTag(Element element) {
        return isParserTag(element, HTML_PARSERS);
    }

    private static <T extends HtmlParser> T getParser(String tag, List<T> parsers) {
        for (T componentHtmlParser : parsers) {
            if (componentHtmlParser.getId().equalsIgnoreCase(tag)) {
                return componentHtmlParser;
            }
        }
        return null;
    }

    private static boolean isParserTag(Element element, List<? extends HtmlParser> parsers) {
        for (HtmlParser componentHtmlParser : parsers) {
            if (componentHtmlParser.getId().equalsIgnoreCase(element.tagName)) {
                return true;
            }
        }
        return false;
    }

}
