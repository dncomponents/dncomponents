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
import com.dncomponents.client.views.HasAttributes;
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
                final IsElement component = HtmlParserService.getComponentParser(at.tagName).parse(at, elementsMap);
                if (component != null) {
                    if (component instanceof HasAttributes) {
                        for (String attributeName : ((HasAttributes) component).getAttributeNames()) {
                            if (at.attributes.asList().stream().map(e -> e.name)
                                    .anyMatch(e -> e.equals(attributeName))) {
                                final String attributeValue = at.getAttribute(attributeName);
                                final String between = getBetween(attributeValue);
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
        try {
            return text.substring(text.indexOf(c1) + c1.length(), text.indexOf(c2));
        } catch (Exception ex) {
            return null;
        }
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
            for (Attr attr : at.attributes.asList()) {
                if (attr.name.startsWith("on-")) {
                    final String[] split = attr.name.split("-");
                    String value = attr.value;
                    String name = attr.name;
                    if (value != null) {
                        at.addEventListener(split[1], evt -> {
                            if (eventHandlers.get(value) != null) {
                                eventHandlers.get(value).accept(evt);
                                updateAll();
                                fireCustomEvent(at, "update", "updated");
                            }
                        });
                    }
                    at.removeAttribute(attr.name);
                }
            }
            if (at.hasAttribute("bind")) {
                final String value = getBetween(at.getAttribute("value"));
                final String checked = getBetween(at.getAttribute("checked"));
                if (at instanceof HTMLTextAreaElement) {
                    at.addEventListener("input", evt -> {
                        final Consumer<Event> eventConsumer = eventHandlers.get("textarea:" + value);
                        if (eventConsumer != null) {
                            eventConsumer.accept(evt);
                            updateAll();
                            fireCustomEvent(at, "update", "updated");
                        }
                    });
                }
                if (at instanceof HTMLInputElement) {
                    if (((HTMLInputElement) at).type == "radio" || ((HTMLInputElement) at).type == "checkbox") {
                        at.addEventListener("change", evt -> {
                            final Consumer<Event> eventConsumer = eventHandlers.get("radio:" + checked);
                            if (eventConsumer != null) {
                                eventConsumer.accept(evt);
                                updateAll();
                                fireCustomEvent(at, "update", "updated");

                            }
                        });
                    } else {
                        at.addEventListener("input", evt -> {
                            final Consumer<Event> eventConsumer = eventHandlers.get("input:" + value);
                            if (eventConsumer != null) {
                                eventConsumer.accept(evt);
                                updateAll();
                                fireCustomEvent(at, "update", "updated");
                            }
                        });
                    }

                }
            }
        }
    }


    private void parseIfs(Node root) {
        NodeList<Element> elements = root.querySelectorAll("*");
        for (int i = 0; i < elements.length; i++) {
            Element at = elements.getAt(i);
            if (at.attributes == null || at.attributes.length == 0) continue;
            if (at.hasAttribute("if")) {
                List<Pair<String, HTMLElement>> ifElseElements = new ArrayList<>();
                ifElseElements.add(new Pair<>(getBetween(at.getAttribute("if")), Js.cast(at)));

                Element nextElementSibling = at;
                while (true) {
                    nextElementSibling = nextElementSibling.nextElementSibling;
                    if (nextElementSibling == null || !(nextElementSibling.hasAttribute("else-if")
                            || nextElementSibling.hasAttribute("else"))) break;

                    if (nextElementSibling.hasAttribute("else-if")) {
                        ifElseElements.add(new Pair<>(getBetween(nextElementSibling.getAttribute("else-if")), Js.cast(nextElementSibling)));
                    }
                    if (nextElementSibling.hasAttribute("else")) {
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

    private Map<String, Map<String, Function>> allLoopsFunctionsMap = new HashMap<>();
    Map<String, Map<String, BiConsumer>> allLoopEventsHandlers = new HashMap<>();
    Object loopValue;
    String loopName;
    Map<String, Function> loopFunctions = new HashMap<>();
//    Map<String, BiConsumer> loopHandlers = new HashMap<>();

    public void setLoopStateFunctions(String collectionName, Map map) {
        allLoopsFunctionsMap.put(collectionName, map);
//        for (UpdateUi updateUi : multiMapValueElements.get(collectionName)) {
//            if (updateUi instanceof LoopElement) {
//                ((LoopElement) updateUi).setFunctions(map);
//            }
//        }
    }

    public void setLoopEventHandlers(String collectionName, Map<String, BiConsumer> map) {
        allLoopEventsHandlers.put(collectionName, map);
//        for (UpdateUi updateUi : multiMapValueElements.get(collectionName)) {
//            if (updateUi instanceof LoopElement) {
//                ((LoopElement) updateUi).setLoopEventHandlers(map);
//            }
//        }
    }

    public void addStateFunction(String stateName, Supplier supplier) {
        states.add(new State(stateName, supplier, this));
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

    class IfElseElement extends AbstractElementValue {
        List<Pair<String, HTMLElement>> ifElseElements;

        public IfElseElement(List<Pair<String, HTMLElement>> ifElseElements) {
            super(ifElseElements.get(0).getB(), null, null);
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

    private List<Text> getTextNodes(HTMLElement element) {
        List<Text> textNodes = new ArrayList<>();
        for (Node childNode : element.childNodes.asList()) {
            if (childNode instanceof Text) {
                Text textNode = (Text) childNode;
                textNodes.add(textNode);
            }
        }
        return textNodes;
    }

    class ElementValueTag extends AbstractElementValue {

        List<String> textNodesStrings;

        public ElementValueTag(HTMLElement element, List<String> textNodes) {
            super(element, "", "");
            this.textNodesStrings = textNodes;
        }

        @Override
        public void update(Object value) {
            final List<Text> textNodes = getTextNodes(element);
            for (int i = 0; i < textNodesStrings.size(); i++) {
                Text newNode = DomGlobal.document.createTextNode(replaceAll(textNodesStrings.get(i)));
                // Replace the old text node with the new one
                element.replaceChild(newNode, textNodes.get(i));
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
