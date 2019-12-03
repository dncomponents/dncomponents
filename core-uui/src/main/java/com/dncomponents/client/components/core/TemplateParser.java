package com.dncomponents.client.components.core;

import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.Ui;
import elemental2.dom.*;

import java.util.HashMap;
import java.util.Map;

public class TemplateParser {

    private HTMLTemplateElement templateElement;

    private Map elementsMap = new HashMap();

    private Node clonedNode;

    private String templateContent;

    String clazz = "";

    public TemplateParser(HTMLTemplateElement templateElement) {
        this.templateElement = templateElement;
    }

    public TemplateParser(HTMLTemplateElement templateElement, boolean init) {
        this.templateElement = templateElement;
        if (init) init();
    }

    public TemplateParser(String templateContent) {
        this.templateContent = templateContent;
    }

    public TemplateParser(String templateContent, boolean init) {
        this.templateContent = templateContent;
        if (init) init();
    }

    public void init() {
        if (templateElement == null)
            if (templateContent != null && templateContent.startsWith("#")) { //id of template
                String str = templateContent.substring(1);
                this.templateElement = ((HTMLTemplateElement) DomGlobal.document.getElementById(str));
            } else {
                this.templateElement = (HTMLTemplateElement) DomGlobal.document.createElement("template");
                this.templateElement.innerHTML = templateContent;
            }
        elementsMap.clear();
        i18e(this.templateElement);
        this.clonedNode = this.templateElement.content.cloneNode(true);
        parseTemplates(TEMPLATE_KEY, this.clonedNode);
        parseElements(KEY, this.clonedNode);
        parseOther(KEY, this.clonedNode);
        clearKeyTags(KEY, getCloned());
    }

    public static String KEY = "ui-field";

    public static String TEMPLATE_KEY = "ui-template";

    public <T> T getElement(String id) {
        return (T) this.elementsMap.get(id);
    }

    public <T extends Element> T getHTMLElement(String id) {
        return (T) this.elementsMap.get(id);
    }

    public <T extends IsElement> T getIsElement(String id) {
        return (T) this.elementsMap.get(id);
    }

    public String getStyle(String id) {
        return (String) this.elementsMap.get(id);
    }

    public Node getCloned() {
        return clonedNode;
    }

    private static void clearKeyTags(String key, Node root) {
        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            if (at.hasAttribute(KEY) && Ui.isDebug()) {
                at.setAttribute("ui-field-debug", at.getAttribute(KEY));
            }
            at.removeAttribute(key);
        }

    }

    private Map<String, ?> mapCustomElements(String key, Node root) {
        Map<String, Object> result = null;
        NodeList<Element> allElements = root.querySelectorAll("*");
        for (int i = 0; i < allElements.length; i++) {
            Element at = allElements.getAt(i);
            if (HtmlParserService.isComponentParserTag(at)) {
                result = new HashMap<>();
                String value = at.getAttribute(key);
                if (elementsMap.containsKey(value))
                    throw new IllegalStateException("You can't have duplicate ui-field: " + value + " for class: " + clazz);
                IsElement component = HtmlParserService.getComponentParser(at.tagName).parse(at, elementsMap);
                if (component != null) {
                    if (value != null)
                        result.put(value, component);
                }
                component.asElement().removeAttribute(key);
                break;
            }
        }
        return result;
    }

    private void parseElements(String key, Node root) {
        while (true) {
            Map<String, ?> mapCustomElements = mapCustomElements(key, root);
            if (mapCustomElements == null) {
                break;
            }
            elementsMap.putAll(mapCustomElements);
        }
        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            if (at.attributes == null || at.attributes.length == 0) continue;
            String value = at.getAttribute(key);
            if (elementsMap.containsKey(value))
                throw new IllegalStateException("You can't have duplicate ui-field: " + value + " for class: " + clazz);
            if (value != null) {
                elementsMap.put(value, at);
            }
        }
    }

    private void parseOther(String key, Node root) {
        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            if (HtmlParserService.isParserTag(at)) {
                Object parsed = HtmlParserService.getParser(at.tagName).parse(at, elementsMap);
                String value = at.getAttribute(key);
                if (parsed != null && value != null) {
                    elementsMap.put(value, parsed);
                }
            }
        }
    }


    private void parseTemplates(String key, Node root) {
        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            String value = at.getAttribute(key);
            if (value != null)
                elementsMap.put(value, at);
        }

    }

    private void i18e(HTMLTemplateElement template) {
        if (template != null && template.innerHTML.contains(DnI18e.START_TAG)) {
            String content = template.innerHTML;
            do {
                final String between = DnI18e.getBetween(content, DnI18e.START_TAG, DnI18e.END_TAG);
                if (between == null || between.isEmpty())
                    break;
                String value = DnI18e.get().getValue(between);
                //todo ? SafeHtmlUtils.htmlEscape()
                content = content.replace(DnI18e.START_TAG + between + DnI18e.END_TAG, value);
            } while (true);
            template.innerHTML = content;
        }
    }

}