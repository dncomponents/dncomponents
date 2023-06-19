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

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.lang.model.element.ElementKind;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nikolasavic
 */
public class ValuesProcessing {

    private Set<String> valuesNames = new HashSet<>();
    private String imports = "";
    String updates = "";
    String initFunctions = "";

    javax.lang.model.element.Element classEl;


    public ValuesProcessing(String html, javax.lang.model.element.Element classEl) {
        this.classEl = classEl;
        parse(html);
    }

    String getStateName(String valueName) {
        String key = valueName;
        if (key.contains(".") && !checkIfItsMethod(key)) {
            final String[] split = key.split("\\.");
            key = split[0];
        }
        return key;
    }

    List<String> fromLoopValues = new ArrayList<>();

    public void parse(String html) {
        if (html == null)
            return;

        Document doc = Jsoup.parse(html);

        final Elements dloop = doc.getElementsByAttribute("loop");
        for (org.jsoup.nodes.Element element : dloop) {
            final String loop = element.attributes().get("loop");
            final String[] split = loop.split(" ");
            String avoidName = split[0];
            final List<String> valueFromLoop = findSubstrings(element.html()).stream().filter(e ->
                    !avoidName.equals(getStateName(e))).collect(Collectors.toList());
            fromLoopValues.addAll(valueFromLoop);
            element.remove();
        }

        List<String> allStateNames = findSubstrings(doc.html());
        if (allStateNames != null) {
            valuesNames = allStateNames.stream().collect(Collectors.toSet());
            valuesNames.addAll(fromLoopValues);
        }
        Set<String> states = new HashSet<>();

        for (String valuesName : valuesNames) {
            if (Util.checkIfItsMethod(valuesName)) {
                updates += "        template.updateState(\"" + StringEscapeUtils.escapeJava(valuesName) + "\"," +
                        " d." + Util.replaceFunctionArguments(valuesName) + ",true);\n";
            } else if (valuesName.contains(".")) {
                final String[] split = valuesName.split("\\.");
                states.add(split[0]);
            } else {
                states.add(valuesName);
            }
        }
        for (String name : states) {
            updates += "        template.updateState(\"" + name + "\", d." + name + ",true);\n";
            final String fn = checkForFunctions(name, valuesNames, checkCollectionType(name, classEl));
            if (!fn.isEmpty()) {
                initImports();
                initFunctions += "          template.getState(\"" + name + "\").setFunctionMap(" + fn + "\n";
            }
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

    private void initImports() {
        if (imports.isEmpty())
            imports = "import java.util.HashMap;\n" +
                    "import java.util.function.Function;\n";
    }

    private String checkForFunctions(String fieldName, Set<String> valuesNames, String type) {
        String result = "";
        List<String> functions = new ArrayList<>();
        for (String valuesName : valuesNames) {
            if (valuesName.startsWith(fieldName + ".")) {
                functions.add(valuesName);
            }
        }
        if (!functions.isEmpty()) {
            String fns = "";
            for (String function : functions) {
                fns += "                    put(\"" + StringEscapeUtils.escapeJava(function) + "\", (Function<" + type + ", Object>)" + fieldName + " -> " + function + ");\n";
            }
            result = "new HashMap() {{\n" + fns + "                }});";
        }
        return result;
    }

    public String getUpdates() {
        return updates;
    }

    public String getInitFunctions() {
        return initFunctions;
    }

    private String checkCollectionType(String collectionName, javax.lang.model.element.Element classEl) {
        final Optional<? extends javax.lang.model.element.Element> optionalElement = classEl.getEnclosedElements().stream()
                .filter(e -> e.getKind() == ElementKind.FIELD && e.getSimpleName().toString().equals(collectionName))
                .findAny();
        if (optionalElement.isPresent()) {
            final javax.lang.model.element.Element element = optionalElement.get();
            return element.asType() + "";
        }
        return "";
    }

    public String getImports() {
        return imports;
    }
}
