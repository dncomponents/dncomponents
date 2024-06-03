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

package com.dncomponents;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dncomponents.EventsProcessing.isModel;


public class LoopProcessing {

    String imports = "";
    private Set<String> updateSet;
    private Set<String> eventsSet;

    Multimap<String, Loop> allLoopsMap = LinkedListMultimap.create();
    private String initFunctions = "";
    javax.lang.model.element.Element classEl;

    Map<String, String> loopInLoops = new HashMap<>();


    public LoopProcessing(String html, javax.lang.model.element.Element classEl, Set<String> updateSet, Set<String> eventsSet) {
        this.classEl = classEl;
         this.updateSet = updateSet;
        this.eventsSet = eventsSet;
        parse(html);
    }

    private String checkCollectionType(String collectionName) {
        try {
            CollectionTypeChecker checker = new CollectionTypeChecker(classEl);
            return checker.checkCollectionType(collectionName);
        } catch (Exception ex) {
            return "";
        }
    }

    private boolean isLoopInLoop(String loopName) {
        return loopInLoops.get(loopName) != null;
    }

    private Loop getParentLoop(String loopName) {
        if (!allLoopsMap.get(loopInLoops.get(loopName)).isEmpty()) {
            return allLoopsMap.get(loopInLoops.get(loopName)).stream().findFirst().get();
        }
        return null;
    }

    public void parse(String html) {
        if (html == null)
            return;
        Document doc = Jsoup.parse(html);
        final Elements allElements = doc.getAllElements();
        for (int i = 0; i < allElements.size(); i++) {
            if (allElements.get(i).attributes() == null || allElements.get(i).attributes().size() == 0) continue;
            String value = allElements.get(i).attributes().get("dn-loop");
            if (value != null && !value.isEmpty()) {
                final String[] words = value.split(" ");

                final Loop loop = new Loop(words[0], words[2],
                        parseLoopValues(allElements.get(i), words[0], words[2]),
                        parseLoopEventHandlers(allElements.get(i)));
                allLoopsMap.put(words[2], loop);
            }
        }


        allLoopsMap.asMap().forEach((collectionName, loops) -> {
            String type;
            if (!isLoopInLoop(collectionName)) {
                updateSet.add("        template.addStateFunction(\"" + collectionName + "\", () -> d." + collectionName + ");\n");
                type = checkCollectionType(collectionName);
                for (Loop loop : loops) {
                    loop.setType(type);
                }
            } else {
                type = getParentLoop(collectionName).type;
            }

            String eh = String.join("", loops.stream()
                    .flatMap((Function<Loop, Stream<String>>) loop -> checkForEventHandlers(loop, type).stream())
                    .collect(Collectors.toSet()));

            String fn = String.join("", loops.stream()
                    .flatMap((Function<Loop, Stream<String>>) loop -> checkForFunctions(loop, type).stream())
                    .collect(Collectors.toSet()));


            if (!fn.isEmpty()) {
                fn = " new HashMap() {{\n" + fn + "        }});";
                initFunctions += "        template.setLoopStateFunctions(\"" + collectionName + "\"," + fn + "         \n";
            }
            if (!eh.isEmpty()) {
                eh = " new HashMap() {{\n" + eh + "        }});";
                initFunctions += "\n        template.setLoopEventHandlers(\"" + collectionName + "\"," + eh + "         \n";
            }
        });
        updateSet.add(initFunctions);
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
            initFnImports();
            initEventsImports();
            String fns = "";
            for (Map.Entry<String, String> entry : loop.eventHandlers.entrySet()) {
                if (isModel(entry.getKey())) {
                    if (entry.getValue().startsWith(loop.arg + ".") || entry.getValue().equals(loop.arg)) {
                        fns = "            put(\"" + StringEscapeUtils.escapeJava(entry.getKey()) +
                              "\", (BiConsumer<Event," + type + ">)(e," + loop.arg + ") -> {" +
                              EventsProcessing.getModelAssign(StringEscapeUtils.escapeJava(entry.getKey()), entry.getValue(), true) + "});\n";
                        loop.functions.add(entry.getValue());
                    } else {
                        updateSet.add("        template.addStateFunction(\"" + StringEscapeUtils.escapeJava(entry.getValue()) + "\", () -> " +
                                      Util.createJavaCode(entry.getValue(), null, false) + ");\n");
                        EventsProcessing.setEventStringHandler(entry.getKey(), entry.getValue(), eventsSet);
                    }
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


    public Set<String> parseLoopValues(Element element, String arg, String loopName) {
        final Elements loopInLoop = element.getElementsByAttribute("dn-loop");
        Set<String> loopInLoopFns = new HashSet<>();
        if (loopInLoop.size() > 1) {
            Element firstLoop = loopInLoop.get(1);
            final String[] fns = StringUtils.substringsBetween(firstLoop.html(), "{{", "}}");
            if (fns != null) {
                loopInLoopFns = Arrays.stream(fns)
                        .filter(s -> s.startsWith(arg))
                        .collect(Collectors.toSet());
            }
            final String[] words = firstLoop.attributes().get("dn-loop").split(" ");
            loopInLoopFns.add(words[2]);
            loopInLoops.put(words[2], loopName);
            firstLoop.remove();
        }
        Set<String> valuesNames = new HashSet<>();
        valuesNames.addAll(loopInLoopFns);
        final String[] strings = org.apache.commons.lang3.StringUtils.substringsBetween(element.html(), "{{", "}}");
        final Set<String> loopFns = Arrays.stream(strings).collect(Collectors.toSet());

        Elements elements = element.select("[dn-if], [dn-else], [dn-else-if]");
        for (Element el : elements) {
            // Extract "dn-if" attribute value
            String dnIfValue = el.attr("dn-if");
            if (!dnIfValue.isEmpty()) {
                loopFns.add(dnIfValue);
            }

            // Extract "dn-else" attribute value
            String dnElseValue = el.attr("dn-else");
            if (!dnElseValue.isEmpty()) {
                loopFns.add(dnElseValue);
            }

            // Extract "dn-else-if" attribute value
            String dnElseIfValue = el.attr("dn-else-if");
            if (!dnElseIfValue.isEmpty()) {
                loopFns.add(dnElseIfValue);
            }
        }

        if (strings != null) {
            if (isLoopInLoop(loopName)) {
                for (Loop loop : allLoopsMap.get(loopInLoops.get(loopName))) {
                    loopFns.removeIf(e -> e.startsWith(loop.arg + "."));
                }
            }
            valuesNames.addAll(loopFns);
        }
        return valuesNames;
    }

    public Map<String, String> parseLoopEventHandlers(Element element) {
        final EventsProcessing eventsProcessing = new EventsProcessing(element.html(), updateSet, new HashSet<>(), true);
        imports += eventsProcessing.getImports();
        return eventsProcessing.getAllEventsMap(element.html());
    }

    public String getImports() {
        return imports;
    }


    class Loop {

        private String type;

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

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
