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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nikolasavic
 */
public class EventsProcessing {
    LinkedHashSet<String> eventsListAttributes = new LinkedHashSet<>();
    private String imports = "";
    private Set<String> importsSet = new HashSet<>();


    public String parse(String html) {
        if (html == null)
            return "";
        Document doc = Jsoup.parse(wrapToTemplate(html));
        final Elements allElements = doc.getAllElements();
        for (Element element : allElements) {
            if (element.attributes() == null || element.attributes().size() == 0) continue;
            for (Attribute attribute : element.attributes()) {
                if (attribute.getKey().startsWith("on-")) {
                    final String[] split = attribute.getKey().split("-");
                    if (split.length > 0)
                        eventsListAttributes.add(split[1]);
                } else if (element.hasAttr("bind")) {
                    eventsListAttributes.add("bind");
                }
            }
        }
        String generated = "";
        for (String keyEvent : eventsListAttributes) {
            int n = 0;
            int bind = 0;
            for (int i = 0; i < allElements.size(); i++) {
                final Element element = allElements.get(i);
                if (element.attributes() == null || element.attributes().size() == 0) continue;
                String value = element.attributes().get("on-" + keyEvent);
                boolean update = false;
                if (value.isEmpty()) {
                    value = element.attributes().get("on-" + keyEvent + "-u");
                    if (!value.isEmpty()) {
                        update = true;
                    }
                }
                if (value != null && !value.isEmpty()) {
                    final String[] split = value.split(";");
                    String code = "";
                    for (String s : split) {
                        code += "d." + s + ";";
                    }
                    generated += "template.getEventsElementMap().get(\"" + keyEvent + "\").get(" + n++ + ").addEventListener(\"" + keyEvent + "\", e -> {\n" +
                            "                " + code + "\n" +
                            (update ? "             updateUi();\n" : "") +
                            "        });\n\t\t";
                }
                if (element.hasAttr("bind") && keyEvent.equals("bind")) {
                    final String valueAttribute = element.attributes().get("value");
                    final String checkedAttribute = element.attributes().get("checked");
                    if (valueAttribute.startsWith("{{")) {
                        if (element.tagName().equals("input")) {
                            importsSet.add("import elemental2.dom.HTMLInputElement;\n");
                            generated += "template.getEventsElementMap().get(\"bind\").get(" + bind++ + ").addEventListener(\"input\", e -> {\n" +
                                    "                d." + getBetween(valueAttribute) + "=((HTMLInputElement) e.target).value;\n" +
                                    "             updateUi();\n" +
                                    "        });\n\t\t";
                        } else if (element.tagName().equals("textarea")) {
                            importsSet.add("import elemental2.dom.HTMLTextAreaElement;\n");
                            generated += "template.getEventsElementMap().get(\"bind\").get(" + bind++ + ").addEventListener(\"input\", e -> {\n" +
                                    "                d." + getBetween(valueAttribute) + "=((HTMLTextAreaElement) e.target).value;\n" +
                                    "             updateUi();\n" +
                                    "        });\n\t\t";

                        } else if (element.tagName().equals("select")) {
                            //todo
                        }
                    } else if (checkedAttribute.startsWith("{{")) {
                        if (element.tagName().equals("input") && (element.attributes().get("type").equals("checkbox")
                                || element.attributes().get("type").equals("radio"))) {
                            importsSet.add("import elemental2.dom.HTMLInputElement;\n");
                            generated += "template.getEventsElementMap().get(\"bind\").get(" + bind++ + ").addEventListener(\"change\", e -> {\n" +
                                    "                d." + getBetween(checkedAttribute) + "=(((HTMLInputElement) e.target).checked);\n" +
                                    "             updateUi();\n" +
                                    "        });\n\t\t";

                        }
                    }
                }
            }
        }

        String result = "";
        if (!generated.isEmpty()) {
            result = "    public void bindEvents(){\n" +
                    "           " + generated + "\n" +
                    "   }\n";
        }

        return result;
    }

    private String wrapToTemplate(String html) {
        String res="<template>"+html+"</template>";
        return res;
    }

    private String getBetween(String text) {
        String c1 = "{{", c2 = "}}";
        try {
            return text.substring(text.indexOf(c1) + c1.length(), text.indexOf(c2));
        } catch (Exception ex) {
            return null;
        }
    }

    public String getImports() {
        return importsSet.stream().collect(Collectors.joining());
    }
}
