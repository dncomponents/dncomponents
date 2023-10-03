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

package com.dncomponents;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.lang.model.element.ElementKind;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dncomponents.EventsProcessing.isBinder;

/**
 * @author nikolasavic
 */
public class LoopProcessing {

    String updates = "";
    String imports = "";

    Multimap<String, Loop> collections = LinkedListMultimap.create();
    private String initFunctions = "";
    javax.lang.model.element.Element classEl;

    public LoopProcessing(String html, javax.lang.model.element.Element classEl) {
        this.classEl = classEl;
        parse(html);
    }


    private String checkCollectionType(String collectionName) {
        final Optional<? extends javax.lang.model.element.Element> optionalElement = classEl.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.FIELD && e.getSimpleName().toString().equals(collectionName))
                .findAny();
        if (optionalElement.isPresent()) {
            final javax.lang.model.element.Element element = optionalElement.get();
            try {
                return ((Type.ClassType) ((Symbol.VarSymbol) element).type).typarams_field.get(0).toString();
            } catch (Exception ex) {

            }
        }
        return "";
    }

    public void parse(String html) {
        if (html == null)
            return;
        Document doc = Jsoup.parse(html);
        final Elements allElements = doc.getAllElements();
        for (int i = 0; i < allElements.size(); i++) {
            if (allElements.get(i).attributes() == null || allElements.get(i).attributes().size() == 0) continue;
            String value = allElements.get(i).attributes().get("loop");
            if (value != null && !value.isEmpty()) {
                final String[] words = value.split(" ");
                collections.put(words[2], new Loop(words[0], words[2],
                        parseLoop(allElements.get(i)),
                        parseEventHandlers(allElements.get(i))));
            }
        }

        collections.asMap().forEach((collectionName, loops) -> {
            updates += "        template.addStateFunction(\"" + collectionName + "\",()-> d." + collectionName + ");\n";

            String fn = String.join("", loops.stream()
                    .flatMap((Function<Loop, Stream<String>>) loop -> checkForFunctions(loop, checkCollectionType(collectionName)).stream())
                    .collect(Collectors.toSet()));

            String eh = String.join("", loops.stream()
                    .flatMap((Function<Loop, Stream<String>>) loop -> checkForEventHandlers(loop, checkCollectionType(collectionName)).stream())
                    .collect(Collectors.toSet()));

            if (!fn.isEmpty()) {
                fn = "new HashMap() {{\n" + fn + "        }});";
                initFunctions += "        template.setLoopStateFunctions(\"" + collectionName + "\"," + fn + "         ";
            }
            if (!eh.isEmpty()) {
                eh = "new HashMap() {{\n" + eh + "        }});";
                initFunctions += "\n        template.setLoopEventHandlers(\"" + collectionName + "\"," + eh + "         ";
            }
        });
    }

    private Set<String> checkForFunctions(Loop loop, String type) {
        Set<String> functionsSet = new HashSet();
        if (!loop.functions.isEmpty()) {
            initFnImports();
            for (String function : loop.functions) {
                function = function.replace("&quot;", "\"");
                String fns = "            put(\"" + StringEscapeUtils.escapeJava(function) + "\", (Function<" + type + ", Object>)" + loop.arg + " -> " + Util.createJavaCode(function, loop.arg, false) + ");\n";
                functionsSet.add(fns);
            }
        }
        return functionsSet;
    }

    private Set<String> checkForEventHandlers(Loop loop, String type) {
        Set<String> eventHandlersSet = new HashSet<>();
        if (!loop.eventHandlers.isEmpty()) {
            initEventsImports();
            String fns = "";
            for (Map.Entry<String, String> entry : loop.eventHandlers.entrySet()) {
                if (isBinder(entry.getKey())) {
                    fns = "            put(\"" + StringEscapeUtils.escapeJava(entry.getKey()) + "\", (BiConsumer<Event," + type + ">)(e," + loop.arg + ") -> {" + Util.createJavaCode(entry.getValue(), loop.arg, true).replace(";", "") + EventsProcessing.getBinderAssign(entry.getKey()) + "});\n";
                } else {
                    fns = "            put(\"" + StringEscapeUtils.escapeJava(entry.getKey()) + "\", (BiConsumer<Event," + type + ">)(e," + loop.arg + ") -> {" + Util.createJavaCode(entry.getValue(), loop.arg, true) + "});\n";
                }
                eventHandlersSet.add(fns);
            }
        }
        return eventHandlersSet;
    }

    private void initFnImports() {
        String fnImports = "import java.util.HashMap;\n" +
                "import java.util.function.Function;\n";
        if (!imports.contains(fnImports)) {
            imports += fnImports;
        }
    }

    private void initEventsImports() {
        String eventHandlerImports = "import java.util.function.BiConsumer;\n" +
                "import elemental2.dom.Event;\n";
        if (!imports.contains(eventHandlerImports)) {
            imports += eventHandlerImports;
        }
    }

    public Set<String> parseLoop(Element element) {
        Set<String> valuesNames = new HashSet<>();
        if (element == null)
            return valuesNames;
        final String[] strings = org.apache.commons.lang3.StringUtils.substringsBetween(element.html(), "{{", "}}");
        if (strings != null)
            valuesNames.addAll(Arrays.stream(strings).collect(Collectors.toSet()));
        return valuesNames;
    }

    public Map<String, String> parseEventHandlers(Element element) {
        final EventsProcessing eventsProcessing = new EventsProcessing();
        imports += eventsProcessing.getImports();
        return eventsProcessing.getAllEventsMap(element.html());
    }

    public String getInitFunctions() {
        return initFunctions;
    }

    public String getUpdates() {
        return updates;
    }

    public String getImports() {
        return imports;
    }

    class Loop {

        public Loop(String arg, String collectionName, Set<String> functions, Map<String, String> eventHandlers) {
            this.arg = arg;
            this.collectionName = collectionName;
            this.functions = functions;
            this.eventHandlers = eventHandlers;
        }

        String arg;
        String collectionName;
        Map<String, String> eventHandlers;
        private Set<String> functions = new HashSet<>();

    }
}
