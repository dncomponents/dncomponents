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


import com.dncomponents.client.dom.MultiMap;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.*;
import jsinterop.base.Js;

import java.util.*;

public class TemplateParser {

    private static boolean debug;

    private HTMLTemplateElement templateElement;

    private Map elementsMap = new HashMap();

    private Map<String, String> valueMap = new HashMap<>();

    //looks for element value <div>{{value}}</div>
    private MultiMap<String, ElementValue> multiMapValueElements = new MultiMap<>();

    //looks for element attribute value <div title='{{value}}>Something</div>
    private MultiMap<String, ElementValue> multiMapValueAttributeElements = new MultiMap<>();

    //looks for style properties <div style='color:{{value}}>Something</div>
    private MultiMap<String, ElementValue> multiMapValueStyleAttributeElements = new MultiMap<>();
    private Map<String, ArrayList<Element>> eventsElementMap = new HashMap<>();

    //events list to search for
    List<String> eventsListAttributes = Arrays.asList("(click)", "(onfocus)");

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
//        i18e(this.templateElement); //todo change string limiter, disabled for now
        this.clonedNode = this.templateElement.content.cloneNode(true);
        parseTemplates(TEMPLATE_KEY, this.clonedNode);
        parseValueElements(this.clonedNode);
        parseElements(KEY, this.clonedNode);
        parseOther(KEY, this.clonedNode);
        parseValueElementsAtAttributes(this.clonedNode);
        parseEventsElements(this.clonedNode);
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
            if (at.hasAttribute(KEY) && isDebug()) {
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
                final IsElement component = HtmlParserService.getComponentParser(at.tagName).parse(at, elementsMap);
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


    private void updateValueElements(String valueElement) {
        for (ElementValue element : multiMapValueElements.get(valueElement)) {
            element.updateValue();
        }
    }

    private void updateValueAttributeElements(String valueElement) {
        for (ElementValue element : multiMapValueAttributeElements.get(valueElement)) {
            element.updateAttribute();
        }
    }

    private void updateValueStyleAttributeElements(String valueElement) {
        for (ElementValue element : multiMapValueStyleAttributeElements.get(valueElement)) {
            element.updateStyle();
        }
    }

    public void updateValueUi(String valueElementName, String value) {
        valueMap.put(valueElementName, value);
        updateValueElements(valueElementName);
        updateValueAttributeElements(valueElementName);
        updateValueStyleAttributeElements(valueElementName);
    }

    public Map<String, ArrayList<Element>> getEventsElementMap() {
        return eventsElementMap;
    }

    private void parseValueElements(Node root) {
        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            if (at.childElementCount == 0 && at.innerHTML.contains("{{")) {
                String content = at.innerHTML;
                final String between = getBetween(content, "{{", "}}");
                if (between == null || between.isEmpty())
                    continue;
                multiMapValueElements.put(between, new ElementValue(Js.cast(at), "", at.innerHTML));
                at.innerHTML = "";
            }
        }
    }


    private String replaceAll(String stringWithValues) {
        String str = stringWithValues;
        for (Map.Entry<String, String> entry : valueMap.entrySet()) {
            String toReplace = DnI18n.START_TAG + entry.getKey() + DnI18n.END_TAG;
            str = new String(str).replaceAll(toReplace, entry.getValue());
        }
        return str;
    }

    private String getBetween(String text, String c1, String c2) {
        try {
            return text.substring(text.indexOf(c1) + c1.length(), text.indexOf(c2));
        } catch (Exception ex) {
            return null;
        }
    }

    private void parseValueElementsAtAttributes(Node root) {
        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            if (at.attributes == null || at.attributes.length == 0) continue;
            for (Attr attr : at.attributes.asList()) {
                if (attr.name.equals("style") && attr.value.contains("{{")) {
                    final String style = at.getAttribute("style");
                    final String[] split = style.split(";");
                    for (String property : split) {
                        final String[] keyValue = property.split(":");
                        if (keyValue.length == 2) {
                            final String between = getBetween(keyValue[1], "{{", "}}");
                            if (between != null) {
                                multiMapValueStyleAttributeElements
                                        .put(between, new ElementValue(Js.cast(at), keyValue[0], keyValue[1]));
                            }
                        }
                    }
                } else if (attr.value.contains("{{")) {
                    final String between = getBetween(attr.value, "{{", "}}");
                    if (between == null || between.isEmpty())
                        continue;
                    multiMapValueAttributeElements.put(between, new ElementValue(Js.cast(at), attr.name, attr.value));
                }
            }
        }
    }

    private void parseEventsElements(Node root) {
        NodeList<Element> elements = root.querySelectorAll("*");
        for (String keyEvent : eventsListAttributes) {
            ArrayList<Element> eventElements = new ArrayList<>();
            for (int i = 0; i < elements.length; i++) {
                Element at = elements.getAt(i);
                if (at.attributes == null || at.attributes.length == 0) continue;
                String value = at.getAttribute(keyEvent);
                if (value != null) {
                    eventElements.add(at);
                    if (!debug)
                        at.removeAttribute(keyEvent);
                }
            }
            if (!eventElements.isEmpty())
                eventsElementMap.put(keyEvent, eventElements);
        }
    }

    private void i18e(HTMLTemplateElement template) {
        if (template != null && template.innerHTML.contains(DnI18n.START_TAG)) {
            String content = template.innerHTML;
            do {
                final String between = getBetween(content, DnI18n.START_TAG, DnI18n.END_TAG);
                if (between == null || between.isEmpty())
                    break;
                String value = DnI18n.get().getValue(between);
                content = content.replace(DnI18n.START_TAG + between + DnI18n.END_TAG, value);
            } while (true);
            template.innerHTML = content;
        }
    }

    public static void setDebug(boolean debug) {
        TemplateParser.debug = debug;
    }

    public static boolean isDebug() {
        return debug;
    }

    class ElementValue {
        HTMLElement element;
        String arg;
        String argValue;

        public ElementValue(HTMLElement element, String arg, String argValue) {
            this.element = element;
            this.arg = arg;
            this.argValue = argValue;
        }

        public void updateAttribute() {
            element.setAttribute(arg, replaceAll(argValue));
        }

        public void updateStyle() {
            element.style.setProperty(arg, replaceAll(argValue));
        }

        public void updateValue() {
            element.innerHTML = replaceAll(argValue);
        }
    }
}