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
import java.util.stream.Collectors;

import static com.dncomponents.Util.checkIfItsMethod;

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
                collections.put(words[2], new Loop(words[0], words[2], parseLoop(allElements.get(i))));
            }
        }

        collections.asMap().forEach((collectionName, loops) -> {
            updates += "                template.updateState(\"" + collectionName + "\", d." + collectionName + ",true);\n";
            String fn = "";
            for (Loop loop : loops) {
                fn += checkForFunctions(loop, checkCollectionType(collectionName));
            }
            if (!fn.isEmpty()) {
                fn = "new HashMap() {{\n" + fn + "                }});";
                initFunctions += "           template.setLoopFunctions(\"" + collectionName + "\"," + fn +
                        "         ";
            }
        });
    }

    private String checkForFunctions(Loop loop, String type) {
        String result = "";
        if (!loop.functions.isEmpty()) {
            initImports();
            String fns = "";
            for (String function : loop.functions) {
                if (checkIfItsMethod(function)) {
                    fns += "                    put(\"" +  StringEscapeUtils.escapeJava(function) + "\", (Function<" + type + ", Object>)" + loop.arg + " -> d." + function + ");\n";

                } else if (function.contains(".")) {
                    fns += "                    put(\"" +  StringEscapeUtils.escapeJava(function) + "\", (Function<" + type + ", Object>)" + loop.arg + " -> " + function + ");\n";

                }

            }
            result = fns;
        }
        return result;
    }


    private void initImports() {
        if (imports.isEmpty())
            imports = "import java.util.HashMap;\n" +
                    "import java.util.function.Function;\n";
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

    private String getBaseName(String nm) {
        if (nm.contains(".")) {
            return nm.split("\\.")[0];
        }
        return null;
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
        public Loop(String arg, String collectionName, Set<String> functions) {
            this.arg = arg;
            this.collectionName = collectionName;
            this.functions = functions;
        }

        String arg, collectionName;
        private Set<String> functions = new HashSet<>();

    }
}
