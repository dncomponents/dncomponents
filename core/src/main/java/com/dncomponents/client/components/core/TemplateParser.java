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


import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.MultiMap;
import com.dncomponents.client.dom.MultiMapArray;
import com.dncomponents.client.views.HasAttributes;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.*;
import jsinterop.base.Js;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TemplateParser {

    private static boolean debug;

    private HTMLTemplateElement templateElement;

    private Map elementsMap = new HashMap();
    private Set<State> states = new HashSet<>();

    /*
    Contains elements that have value <div>{{value}}</div>
     attribute value <div title='{{value}}>Something</div>
     style properties value <div style='color:{{value}}>Something</div>
     loops     <ul loop="per in persons">
                 <li>{{per.getName()}} - {{per.getAge()}}</li>
              </ul>
      component with attributes <my-custom-component color={{color}} person={{person}}>

     key can be a function like <div>{{person.getName()}}</div>
    */
    private MultiMap<String, UpdateUi> multiMapValueElements = new MultiMap<>();
    private MultiMapArray<String, Element> eventsElementMap = new MultiMapArray<>();

    private void initStates() {
        for (Map.Entry<String, Set<UpdateUi>> entry : multiMapValueElements.entrySet()) {
            String key = entry.getKey();
            if (key.contains(".") && !checkIfItsMethod(key)) {
                final String[] split = key.split("\\.");
                key = split[0];
            }
            states.add(new State(key, this));
        }
    }


    public static boolean checkIfItsMethod(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (!Character.isLetter(ch)) {
                if (ch == '(') {
                    return true;
                } else if (ch == '.') {
                    return false;
                }
            }
        }
        return false;
    }

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
        parseLoops(LOOP_KEY, this.clonedNode);
        parseValueElements(this.clonedNode);
        parseElements(KEY, this.clonedNode);
        parseValueElementsAtAttributes(this.clonedNode);
        parseEventsElements(this.clonedNode);
        parseOther(KEY, this.clonedNode);
        initStates();
        clearKeyTags(KEY, getCloned());
    }


    public static String KEY = "ui-field";
    public static String LOOP_KEY = "loop";

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

    public String getStringElement(String id) {
        return (String) this.elementsMap.get(id);
    }

    public State getState(String name) {
        return getStateOptional(name).orElse(null);
    }

    private Optional<State> getStateOptional(String name) {
        return states.stream().filter(s -> s.valueName.equals(name)).findAny();
    }

    public Node getCloned() {
        return clonedNode;
    }

    HTMLElement rootElement;

    public HTMLElement getRoot() {
        return rootElement;
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
                    if (component instanceof HasAttributes) {
                        for (String attributeName : ((HasAttributes) component).getAttributeNames()) {
                            if (at.attributes.asList().stream().map(e -> e.name)
                                    .anyMatch(e -> e.equals(attributeName))) {
                                final String attributeValue = at.getAttribute(attributeName);
                                final String between = getBetween(attributeValue, "{{", "}}");
                                if (between == null || between.isEmpty())
                                    continue;
                                multiMapValueElements.put(between, new ElementValueComponent(component, between, attributeName));
                            }
                        }
                    }
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
            if (i == 0)
                rootElement = (HTMLElement) at;
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

    private void parseLoops(String key, Node root) {
        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            if (at.attributes == null || at.attributes.length == 0) continue;
            for (Attr attr : at.attributes.asList()) {
                if (attr.name.equals(key)) {
                    final String dloop = at.getAttribute(key);
                    final String[] split = dloop.split(" ");
                    String collectionName = split[2];
                    createState(split[0], at);
                    multiMapValueElements.put(collectionName, new LoopElement(at, split[0], collectionName));
                }
            }
        }
    }

    private void createState(String avoid, Element at) {
        final String string = at.innerHTML;
        for (String substring : findSubstrings(string)) {
            String key = substring;
            if (key.contains(".") && !checkIfItsMethod(key)) {
                final String[] split = key.split("\\.");
                key = split[0];
            }
            if (!key.equals(avoid)) {
                states.add(new State(key, this));
            }
        }
    }


    void updateValueUi(String valueElementName, Object value) {
        for (UpdateUi updateUi : multiMapValueElements.get(valueElementName)) {
            updateUi.update(value);
        }
    }


    private void parseValueElements(Node root) {
        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            if (at.childElementCount == 0 && at.innerHTML.contains("{{")) {
                String content = at.innerHTML;
                for (String v : findSubstrings(content)) {
                    multiMapValueElements.put(v, new ElementValueTag(Js.cast(at), "", at.innerHTML));
                }
//                at.innerHTML = "";
            }
        }
    }


    private String replaceAll(String stringWithValues) {
        String str = stringWithValues;
        do {
            final String between = getBetween(str, "{{", "}}");
            if (between == null || between.isEmpty())
                break;
            Object value = getValueFromStates(between);
            str = str.replace("{{" + between + "}}", value + "");
        } while (true);
        return str;
    }

    private Object getValueFromStates(String valueName) {
        for (State state : states) {
            final Object valueByName = state.getValueByName(valueName);
            if (valueByName != null) {
                return valueByName;
            }
        }
        return null;
    }

    private String getBetween(String text, String c1, String c2) {
        try {
            return text.substring(text.indexOf(c1) + c1.length(), text.indexOf(c2));
        } catch (Exception ex) {
            return null;
        }
    }

    private static List<String> findSubstrings(String input) {
        List<String> substrings = new ArrayList<>();
        int startIndex = input.indexOf("{{");
        int endIndex = -1;
        while (startIndex != -1) {
            endIndex = input.indexOf("}}", startIndex);
            if (endIndex != -1) {
                substrings.add(input.substring(startIndex + 2, endIndex));
                startIndex = input.indexOf("{{", endIndex + 2);
            } else {
                break;
            }
        }
        return substrings;
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
                            for (String v : findSubstrings(attr.value)) {
                                multiMapValueElements.put(v, new ElementValueStyle(Js.cast(at), keyValue[0], keyValue[1]));
                            }
                        }
                    }
                } else if (attr.value.contains("{{")) {
                    for (String v : findSubstrings(attr.value)) {
                        multiMapValueElements.put(v, new ElementValueAttribute(Js.cast(at), attr.name, attr.value));
                    }
                }
            }
        }
    }

    private void parseEventsElements(Node root) {
        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            if (at.attributes == null || at.attributes.length == 0) continue;
            for (Attr attr : at.attributes.asList()) {
                if (attr.name.startsWith("on-")) {
                    final String[] split = attr.name.split("-");
                    String value = attr.value;
                    if (value != null) {
                        eventsElementMap.put(split[1], at);
                        if (!debug)
                            at.removeAttribute(attr.name);
                    }
                }
            }
            if (at.hasAttribute("bind")) {
                eventsElementMap.put("bind", at);
            }
        }
    }

    public MultiMapArray<String, Element> getEventsElementMap() {
        return eventsElementMap;
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

    public void updateState(String name, Object value, boolean pending) {
        final State state = getState(name);
        if (state != null) {
            state.setPending(pending);
            state.setValue(value);
        } else {
            DomGlobal.console.log("Warning: You are trying to update state element " + name + " that doesn't exist. Check your html code.");
        }
    }

    public void updateState(String name, Object value) {
        updateState(name, value, false);
    }

    public void updateAll() {
        for (State state : states) {
            state.updateUI();
        }
    }


    public void setLoopFunctions(String collectionName, HashMap hashMap) {
        for (UpdateUi updateUi : multiMapValueElements.get(collectionName)) {
            if (updateUi instanceof LoopElement) {
                ((LoopElement) updateUi).setFunctions(hashMap);
            }
        }
    }

    abstract class AbstractElementValue implements UpdateUi {
        HTMLElement element;
        String arg;
        String argValue;

        public AbstractElementValue(HTMLElement element, String arg, String argValue) {
            this.element = element;
            this.arg = arg;
            this.argValue = argValue;
        }
    }

    class ElementValueAttribute extends AbstractElementValue {

        public ElementValueAttribute(HTMLElement element, String arg, String argValue) {
            super(element, arg, argValue);
        }

        @Override
        public void update(Object value) {
            element.setAttribute(arg, replaceAll(argValue));
            if (element.tagName.equalsIgnoreCase("input") && (element.hasAttribute("checked"))) {
                ((HTMLInputElement) element).checked = element.getAttribute("checked").equalsIgnoreCase("true");
            }
            if (element.tagName.equalsIgnoreCase("textarea") && (element.hasAttribute("value"))) {
                ((HTMLTextAreaElement) element).value = element.getAttribute("value");
            }
            if (element.tagName.equalsIgnoreCase("input")
                    && (element.getAttribute("type") != null
                    && element.getAttribute("type").equalsIgnoreCase("text"))
                    && (element.hasAttribute("value"))) {
                ((HTMLInputElement) element).value = element.getAttribute("value");
            }
        }
    }

    class ElementValueStyle extends AbstractElementValue {

        public ElementValueStyle(HTMLElement element, String arg, String argValue) {
            super(element, arg, argValue);
        }

        @Override
        public void update(Object value) {
            element.style.setProperty(arg, replaceAll(argValue));
        }
    }

    static int eln = 0;

    class ElementValueTag extends AbstractElementValue {

        public ElementValueTag(HTMLElement element, String arg, String argValue) {
            super(element, arg, argValue);
        }

        @Override
        public void update(Object value) {
            element.innerHTML = replaceAll(argValue);
        }
    }

    class LoopElement implements UpdateUi {
        private boolean isTemplate;
        String valueName;
        Element element;
        String collectionName;
        HTMLTemplateElement templateElement = DomUtil.createTemplate();
        Map functions;

        LoopElement(Element element, String valueName, String collectionName) {
            this.valueName = valueName;
            this.element = element;
            if (element instanceof HTMLTemplateElement) {
                isTemplate = true;
            }
            this.collectionName = collectionName;
            templateElement.innerHTML = element.innerHTML;
            this.element.innerHTML = "";
        }

        DocumentFragment fragment = new DocumentFragment();

        public void loop(Collection collection) {
            this.element.innerHTML = "";
            for (Object o : collection) {
                update(valueName, o);
            }
            if (isTemplate) {
                DomUtil.replaceRaw1(fragment, element);
            }
        }

        List<String> getAllStateNames() {
            return states.stream().map(e -> e.valueName).collect(Collectors.toList());
        }

        private void update(String valueName, Object value) {
            TemplateParser parser = new TemplateParser(templateElement, true);
            for (String stateName : getAllStateNames()) {
                if (stateName.equals(valueName))
                    continue;
                final State state1 = parser.getState(stateName);
                if (state1 != null) {
                    state1.setValue(getState(stateName).getValue());
                }
            }
            final State state = parser.getState(valueName);
            if (state != null) {
                state.setFunctionMap(functions);
                state.setValue(value);
            }
            if (isTemplate) {
                fragment.append(parser.getCloned());
//                element.parentElement.appendChild(parser.getCloned());
            } else {
                element.appendChild(parser.getCloned());
            }
        }

        public LoopElement setFunctions(Map<String, Function<?, ?>> functions) {
            Map fns = new HashMap();
            for (Map.Entry<String, Function<?, ?>> entry : functions.entrySet()) {
                final String key = entry.getKey();
                final String[] split = key.split("\\.");
                if (this.valueName.equals(split[0])) {
                    fns.put(entry.getKey(), entry.getValue());
                } else if (key.contains(this.valueName)) { //if it's method
                    fns.put(entry.getKey(), entry.getValue());
                }
            }
            this.functions = fns;
            return this;
        }

        @Override
        public void update(Object value) {
            loop((Collection) value);
        }
    }

    class ElementValueComponent implements UpdateUi {
        Object component;
        String valueName;

        String attributeName;

        public ElementValueComponent(Object component, String valueName, String attributeName) {
            this.component = component;
            this.valueName = valueName;
            this.attributeName = attributeName;
        }

        @Override
        public void update(Object value) {
            if (component instanceof HasAttributes) {
                ((HasAttributes) component).setAttribute(attributeName, value);
            }
        }
    }

}
