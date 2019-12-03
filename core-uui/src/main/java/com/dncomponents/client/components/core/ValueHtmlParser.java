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

