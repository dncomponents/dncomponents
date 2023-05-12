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

    public void parse(String html) {
        if (html == null)
            return;
        Document doc = Jsoup.parse(html);

        final Elements dloop = doc.getElementsByAttribute("loop");
        for (org.jsoup.nodes.Element element : dloop) {
            element.remove();
        }

        final String[] strings = org.apache.commons.lang3.StringUtils.substringsBetween(doc.html(), "{{", "}}");
        if (strings != null)
            valuesNames = Arrays.stream(strings).collect(Collectors.toSet());

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
