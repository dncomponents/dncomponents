package com.dncomponents.client.components.core;

import elemental2.dom.Element;

import java.util.Map;

public interface HtmlParser<T> extends PluginHelper {
    T parse(Element htmlElement, Map<String, ?> elements);
}
