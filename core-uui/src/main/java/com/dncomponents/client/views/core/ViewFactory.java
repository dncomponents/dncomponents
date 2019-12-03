package com.dncomponents.client.views.core;

import com.dncomponents.client.components.core.PluginHelper;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

public interface ViewFactory<T extends View> extends PluginHelper {
    T getView(Map<String, String> attributes, HTMLTemplateElement templateElement);
}
