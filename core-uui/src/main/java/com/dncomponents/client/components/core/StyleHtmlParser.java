package com.dncomponents.client.components.core;

import elemental2.dom.Element;

import java.util.Map;

public class StyleHtmlParser extends AbstractPluginHelper implements HtmlParser<String> {

    private static StyleHtmlParser instance;

    private StyleHtmlParser() {
    }

    public static StyleHtmlParser getInstance() {
        if (instance == null)
            return instance = new StyleHtmlParser();
        return instance;
    }


    @Override
    public String parse(Element htmlElement, Map<String, ?> template) {
        try {
            if (htmlElement.hasAttribute("class")) {
                return htmlElement.getAttribute("class");
            }
            return null;
        } finally {
            htmlElement.remove();
        }
    }

    @Override
    public String getId() {
        return "style-item";
    }

    @Override
    public Class getClazz() {
        return String.class;
    }

}
