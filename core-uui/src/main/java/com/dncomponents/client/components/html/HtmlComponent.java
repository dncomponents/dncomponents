package com.dncomponents.client.components.html;

import com.dncomponents.client.views.IsElement;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public class HtmlComponent implements IsElement {
    final HTMLElement element;

    public HtmlComponent(String tag, String content) {
        element = Js.cast(DomGlobal.document.createElement(tag));
        setHtml(content);
    }

    public void setAttribute(String key, String value) {
        element.setAttribute(key, value);
    }

    @Override
    public HTMLElement asElement() {
        return element;
    }

    public void setHtml(String html) {
        element.innerHTML = html;
    }
}
