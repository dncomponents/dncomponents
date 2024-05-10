/*
 * Copyright 2024 dncomponents
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
import com.dncomponents.client.views.IsElement;
import elemental2.dom.*;
import jsinterop.base.Js;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.dncomponents.client.dom.DomUtil.fireCustomEvent;

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
    private Map<String, Consumer<Event>> eventHandlers = new HashMap<>();
    private Node clonedNode;
    private String templateContent;
    String clazz = "";
    private HTMLElement rootElement;

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
        parseEventsElementsAndSet(this.clonedNode);
        parseIfs(this.clonedNode);
        parseOther(KEY, this.clonedNode);
        clearKeyTags(KEY, getCloned());
    }

    public static String KEY = "ui-field";
    public static String LOOP_KEY = "dn-loop";

    public static String TEMPLATE_KEY = "ui-template";

    public <T> T getElement(String id) {
        return (T) this.elementsMap.get(id);
    }

    public <T extends HTMLElement> T getHTMLElement(String id) {
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
        return states.stream().filter(s -> s.stateName.equals(name)).findAny();
    }

    public Node getCloned() {
        return clonedNode;
    }

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
                Props props = getProps(at);
                IsElement component = null;
                if (props != null) {
                    component = HtmlParserService.getComponentParser(at.tagName).parse(at, elementsMap, props);
                } else {
                    component = HtmlParserService.getComponentParser(at.tagName).parse(at, elementsMap);
                }

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

    private Props getProps(Element at) {
        final Props[] props = {null};
        for (int j = 0; j < at.attributes.getLength(); j++) {
            Attr attr = at.attributes.getAt(j);
            final String attributeValue = attr.value;
            if (attributeValue != null && !attributeValue.startsWith("{{")) {
                if (props[0] == null)
                    props[0] = new Props();
                props[0].putAttribute(attr.name, attributeValue);
            } else {
                final String between = getBetween(attributeValue);
                if (between == null || between.isEmpty())
                    continue;
                getStateOptional(between).ifPresent(state -> {
                    if (props[0] == null)
                        props[0] = new Props();
                    state.setValue();
                    props[0].put(attr.name, state);
                });
            }

        }
        return props[0];
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
                    multiMapValueElements.put(collectionName, new LoopElement(at, split[0], collectionName));
                }
            }
        }
    }

    void updateValueUi(String valueElementName, Object value) {
        for (UpdateUi updateUi : multiMapValueElements.get(valueElementName)) {
            updateUi.update(value);
        }
    }

    /*
     * Search only text nodes for {{
     * then replace it.
     *
     * */
    private void parseValueElements(Node root) {
        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            List<String> textNodesStrings = new ArrayList<>();
            String content = "";
            for (Node node : at.childNodes.asList()) {
                if (node instanceof Text) {
                    textNodesStrings.add(node.textContent);
                    content += node.textContent;
                }
            }
            if (content.contains("{{")) {
                for (String v : findSubstrings(content)) {
                    multiMapValueElements.put(v, new ElementValueTag(Js.cast(at), textNodesStrings));
                }
            }
        }
    }

    private String replaceAll(String stringWithValues) {
        String str = stringWithValues;
        do {
            final String between = getBetween(str);
            if (between == null || between.isEmpty()) {
                break;
            }
            Object value = getValueFromStates(between);
            str = str.replace("{{" + between + "}}", value + "");
        } while (true);
        return str;
    }

    private Object getValueFromStates(String valueName) {
        for (State state : states) {
            if (state.stateName.equals(valueName)) {
                return state.value;
            }
        }
        return null;
    }

    private String getBetween(String text, String c1, String c2) {
        if (text == null) return null;
        int startIndex = text.indexOf(c1);
        if (startIndex == -1) return null; // c1 not found
        startIndex += c1.length();

        int endIndex = text.indexOf(c2, startIndex);
        if (endIndex == -1) return null; // c2 not found

        return text.substring(startIndex, endIndex);
    }

    private String getBetween(String text) {
        return getBetween(text, "{{", "}}");
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
                                multiMapValueElements.put(v, new ElementValueStyle(Js.cast(at), keyValue[0].trim(), keyValue[1].trim()));
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

    public void addEventHandler(String s, Consumer<Event> handler) {
        eventHandlers.put(s, handler);
    }

    private void parseEventsElementsAndSet(Node root) {

        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            if (at.attributes == null || at.attributes.length == 0) continue;
            List<String> eventsToRemove = new ArrayList<>();
            for (Attr attr : at.attributes.asList()) {
                if (attr.name.startsWith("dn-on-")) {
                    final String[] split = attr.name.split("-");
                    String value = attr.value;
                    eventsToRemove.add(value);
                    String name = attr.name;
                    if (value != null) {
                        at.addEventListener(split[2], evt -> {
                            if (eventHandlers.get(value) != null) {
                                eventHandlers.get(value).accept(evt);
                                updateAll();
                                fireCustomEvent(at, "update", "updated");
                            }
                        });
                    }
                }
            }
            eventsToRemove.stream().forEach(s -> at.removeAttribute(s));
            if (at.hasAttribute("dn-model")) {
//                final String modelAttribute = at.getAttribute("dn-model");
                final String model = at.getAttribute("dn-model");
//                final String model = getBetween(modelAttribute);

                if (at.tagName.equalsIgnoreCase("textarea") && model != null) {
                    at.addEventListener("input", evt -> {
                        final Consumer<Event> eventConsumer = eventHandlers.get("textarea:" + model);
                        if (eventConsumer != null) {
                            eventConsumer.accept(evt);
                            updateAll();
                            fireCustomEvent((HTMLElement) at, "update", "updated");
                        }
                    });
                } else if (at.tagName.equalsIgnoreCase("select") && model != null) {
                    at.addEventListener("change", evt -> {
                        final Consumer<Event> eventConsumer = eventHandlers.get("select:" + model);
                        if (eventConsumer != null) {
                            eventConsumer.accept(evt);
                            updateAll(); //todo update only field?
                            fireCustomEvent((HTMLElement) at, "update", "updated");
                        }
                    });
                    multiMapValueElements.put(model, new SelectElement((HTMLElement) at));
                } else if (at.tagName.equalsIgnoreCase("input")
                           && (((HTMLInputElement) at).type.equals("radio") ||
                               ((HTMLInputElement) at).type.equals("checkbox"))
                           && model != null) {

                    multiMapValueElements.put(model, new CheckBoxElementCollection((HTMLElement) at, model));

                    ((HTMLInputElement) at).addEventListener("change", evt -> {
                        final Consumer<Event> eventConsumer = eventHandlers.get("radio:" + model);
                        if (eventConsumer != null) {
                            eventConsumer.accept(evt);
                            updateAll();
                            fireCustomEvent(at, "update", "updated");
                        }
                    });

                } else if (at.tagName.equalsIgnoreCase("input") && model != null) {
                    at.addEventListener("input", evt -> {
                        final Consumer<Event> eventConsumer = eventHandlers.get("input:" + model);
                        if (eventConsumer != null) {
                            eventConsumer.accept(evt);
                            updateAll();
                            fireCustomEvent((HTMLElement) at, "update", "updated");
                        }
                    });
                    multiMapValueElements.put(model, new InputElement((HTMLElement) at));
                }
            }
        }
    }


    private void parseIfs(Node root) {
        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            if (at.attributes == null || at.attributes.length == 0) continue;
            if (at.hasAttribute("dn-if")) {
                List<Pair<String, HTMLElement>> ifElseElements = new ArrayList<>();
                ifElseElements.add(new Pair<>(at.getAttribute("dn-if"), Js.cast(at)));

                Element nextElementSibling = at;
                while (true && nextElementSibling != null) {
                    nextElementSibling = nextElementSibling.nextElementSibling;
                    if (nextElementSibling == null ||
                        !(nextElementSibling.nodeType == Node.ELEMENT_NODE)
                        || !(nextElementSibling.hasAttribute("dn-else-if") ||
                             nextElementSibling.hasAttribute("dn-else"))) {
                        continue;
                    }

                    if (nextElementSibling.hasAttribute("dn-else-if")) {
                        ifElseElements.add(new Pair<>(nextElementSibling.getAttribute("dn-else-if"), Js.cast(nextElementSibling)));
                    }
                    if (nextElementSibling.hasAttribute("dn-else")) {
                        ifElseElements.add(new Pair<>("", Js.cast(nextElementSibling)));
                        break;
                    }
                }
                final IfElseElement resultElement = new IfElseElement(ifElseElements);
                for (Pair<String, HTMLElement> pair : ifElseElements) {
                    if (pair.getA() != null && !pair.getA().isEmpty()) {
                        multiMapValueElements.put(pair.getA(), resultElement);
                    }
                }
            }
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

    public void updateAll() {
        for (State state : states) {
            state.setValue();
        }
        for (State state : states) {
            state.updateUI();
        }
    }

    public void updateAll(boolean exclude, String... fields) {
        for (State state : states) {
            boolean shouldUpdate = exclude;
            for (String field : fields) {
                if (state.stateName.equals(field)) {
                    shouldUpdate = !exclude;
                    break;
                }
            }
            if (shouldUpdate) {
                state.setValue();
                state.updateUI();
            }
        }
    }

    public void updateState(String fieldName) {
        getStateOptional(fieldName).ifPresent(state -> state.updateUI());
    }

    private Map<String, Map<String, Function>> allLoopsFunctionsMap = new HashMap<>();
    Map<String, Map<String, BiConsumer>> allLoopEventsHandlers = new HashMap<>();
    Object loopValue;
    String loopName;
    Map<String, Function> loopFunctions = new HashMap<>();

    public void setLoopStateFunctions(String collectionName, Map map) {
        allLoopsFunctionsMap.put(collectionName, map);
    }

    public void setLoopEventHandlers(String collectionName, Map<String, BiConsumer> map) {
        allLoopEventsHandlers.put(collectionName, map);
    }

    public void addStateFunction(String stateName, Supplier supplier) {
        if (!getStateOptional(stateName).isPresent()) {
            states.add(new State(stateName, supplier, this));
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

        public AbstractElementValue(HTMLElement element) {
            this.element = element;
        }

        public <C extends HTMLElement> C getElement() {
            return (C) element;
        }
    }

    class IfElseElement extends AbstractElementValue {
        List<Pair<String, HTMLElement>> ifElseElements;

        public IfElseElement(List<Pair<String, HTMLElement>> ifElseElements) {
            super(ifElseElements.get(0).getB());
            this.ifElseElements = ifElseElements;
        }

        @Override
        public void update(Object value) {
            boolean found = false;
            for (Pair<String, HTMLElement> ifElseElement : ifElseElements) {
                final Object valueFromStates = getValueFromStates(ifElseElement.getA());
                if (found) {
                    DomUtil.setVisible(ifElseElement.getB(), false);
                }
                if (valueFromStates instanceof Boolean) {
                    final Boolean b = (Boolean) valueFromStates;
                    if (b) {
                        DomUtil.setVisible(ifElseElement.getB(), b);
                        found = true;
                    } else {
                        DomUtil.setVisible(ifElseElement.getB(), b);
                    }
                }
            }
            if (!found) {
                ifElseElements.stream().filter(e -> e.getB().hasAttribute("else"))
                        .findFirst()
                        .ifPresent(pair -> DomUtil.setVisible(pair.getB(), true));
            }
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

    class SelectElement extends AbstractElementValue {

        public SelectElement(HTMLElement element) {
            super(element);
        }

        @Override
        public void update(Object value) {
            if (getElement().multiple) {
                List collection = (List) value;

                HTMLOptionsCollection options = getElement().options;
                for (int i = 0; i < options.getLength(); i++) {
                    options.item(i).selected = (false);
                }
                for (Object o : collection) {
                    for (int i = 0; i < options.getLength(); i++) {
                        if (options.item(i).value.equalsIgnoreCase(o + "")) {
                            options.item(i).selected = (true);
                        }
                    }
                }
            } else {
                getElement().value = (value + "");
            }
        }

        @Override
        public HTMLSelectElement getElement() {
            return super.getElement();
        }
    }

    class CheckBoxElementCollection extends AbstractElementValue {

        public CheckBoxElementCollection(HTMLElement element, String model) {
            super(element);
            if (getElement().type.equalsIgnoreCase("radio")) {
                getElement().name = (model);
            }
        }

        @Override
        public void update(Object value) {
            if (value != null) {
                if (value instanceof Boolean) {
                    getElement().checked = (Boolean.parseBoolean(value.toString()));
                } else if (value instanceof Collection<?>) {
                    Collection<String> collectionValue = (Collection) value;
                    boolean containsIgnoreCase = collectionValue.stream()
                            .anyMatch(str -> str.equalsIgnoreCase(getElement().value));
                    getElement().checked = (containsIgnoreCase);
                } else if (value instanceof String) {
                    if (getElement().value.equalsIgnoreCase(value + "")) {
                        getElement().checked = (true);
                    } else {
                        getElement().checked = (false);
                    }
                }
            } else {
                getElement().checked = (false);
            }
        }

        @Override
        public HTMLInputElement getElement() {
            return super.getElement();
        }
    }


    class InputElement extends AbstractElementValue {

        public InputElement(HTMLElement element) {
            super(element);
        }

        @Override
        public void update(Object value) {
            getElement().value = (value + "");
        }

        @Override
        public HTMLInputElement getElement() {
            return super.getElement();
        }
    }


    private List<Text> getTextNodes(HTMLElement element) {
        List<Text> textNodes = new ArrayList<>();
        for (int i = 0; i < element.childNodes.getLength(); i++) {
            final Node childNode = element.childNodes.item(i);
            if (childNode.nodeType == Node.TEXT_NODE) {
                Text textNode = (Text) childNode;
                if (!textNode.textContent.isEmpty()) {
                    textNodes.add(textNode);
                }
            }
        }
        return textNodes;
    }

    class ElementValueTag extends AbstractElementValue {

        List<String> textNodesStrings;

        public ElementValueTag(HTMLElement element, List<String> textNodes) {
            super(element);
            this.textNodesStrings = textNodes;
        }

        @Override
        public void update(Object value) {
            List<Text> textNodes = getTextNodes(element);
            if (textNodes.isEmpty()) {
                element.appendChild(DomGlobal.document.createTextNode("*"));
                textNodes = getTextNodes(element);
            }
            for (int i = 0; i < textNodes.size(); i++) {
                Text node = textNodes.get(i);
                Text newNode = DomGlobal.document.createTextNode(replaceAll(textNodesStrings.get(i)));
                element.replaceChild(newNode, node);
            }
        }
    }

    class LoopElement implements UpdateUi {
        private boolean isTemplate;
        String valueName;
        Element element;
        String collectionName;
        HTMLTemplateElement templateElement = DomUtil.createTemplate();
        //        Map<String, Function> functions;
//        Map<String, BiConsumer> eventHandlers;
        DocumentFragment fragment;
        List<Node> childNodesList;

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

        public void loop(Collection collection) {
            this.element.innerHTML = "";
            fragment = new DocumentFragment();
            for (Object o : collection) {
                updateValue(o);
            }
            if (isTemplate) {
                if (element.parentElement != null) {
                    //first time loaded
                    childNodesList = new ArrayList<>(fragment.childNodes.asList());
                    DomUtil.replaceRawNodes(fragment, element);
                } else if (childNodesList != null) {
                    //after update state
                    Node firstChild = null;
                    if (collection.isEmpty()) {
                        firstChild = childNodesList.get(0);
                        for (Node node : childNodesList) {
                            if (!node.equals(firstChild)) {
                                node.parentElement.removeChild(node);
                            }
                        }
                        HTMLTemplateElement template = DomUtil.createTemplate();
                        childNodesList = new ArrayList<>(Arrays.asList(template.getRootNode()));
                        DomUtil.replaceRawNodes(template, firstChild);
                    } else {
                        for (Node n : childNodesList) {
                            if (firstChild == null) {
                                firstChild = n;
                                continue;
                            }
                            if (n.parentElement != null)
                                n.parentElement.removeChild(n);
                        }
                        childNodesList = new ArrayList<>(fragment.childNodes.asList());
                        DomUtil.replaceRawNodes(fragment, firstChild);
                    }
                }
            }
        }

        private void setStates(Map<String, Function> functions, Object value, TemplateParser parser) {
            if (functions != null) {
                for (Map.Entry<String, Function> entry : functions.entrySet()) {
                    final String key = entry.getKey();
                    final Object v = entry.getValue().apply(value);
                    if (v instanceof Collection) {
                        final Map childLoopFunctionsMap = allLoopsFunctionsMap.get(key);
                        if (childLoopFunctionsMap != null) {
                            parser.setLoopStateFunctions(key, childLoopFunctionsMap);
                        }
                    }
                    parser.addStateFunction(key, () -> entry.getValue().apply(value));
                }
            }
        }

        private void updateValue(Object value) {
            TemplateParser parser = new TemplateParser(templateElement);
            parser.loopValue = value;
            parser.loopName = collectionName;
            parser.loopFunctions = allLoopsFunctionsMap.get(collectionName);
            setStates(parser.loopFunctions, value, parser);
            if (loopName != null && loopValue != null) {
                setStates(loopFunctions, loopValue, parser);
            }
            parser.allLoopEventsHandlers = allLoopEventsHandlers;
            final Map<String, BiConsumer> loopHandlers = allLoopEventsHandlers.get(collectionName);

            if (loopHandlers != null) {
                for (Map.Entry<String, BiConsumer> entry : loopHandlers.entrySet()) {
                    final String key = entry.getKey();
                    parser.addEventHandler(key, event -> entry.getValue().accept(event, value));
                }
            }
            parser.init();
            parser.updateAll();
            if (isTemplate) {
                fragment.append(parser.getCloned());
            } else {
                element.appendChild(parser.getCloned());
            }
        }

        @Override
        public void update(Object value) {
            loop((Collection) value);
        }
    }

    class Pair<A, B> {
        private final A a;
        private final B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public A getA() {
            return this.a;
        }

        public B getB() {
            return this.b;
        }
    }

}
