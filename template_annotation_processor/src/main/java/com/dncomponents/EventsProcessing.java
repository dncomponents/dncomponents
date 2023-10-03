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
    private Set<String> importsSet = new HashSet<>();


    public String parse(String html) {
        if (html == null)
            return "";
        final Map<String, String> allEventsMap = getAllEventsMap(html);
        String generated = "";
        for (Map.Entry<String, String> entry : allEventsMap.entrySet()) {
            if (isBinder(entry.getKey())) {
                generated += "template.addEventHandler(\"" + entry.getKey() + "\", e -> {\n" +
                        Util.createJavaCode(entry.getValue(), null, true).replace(";", "") + getBinderAssign(entry.getKey()) +
                        "        });\n\t\t";
            } else {
                generated += "template.addEventHandler(\"" + StringEscapeUtils.escapeJava(entry.getKey()) + "\", e -> {\n" +
                        "                " + Util.createJavaCode(entry.getValue(), null, true) + "\n" +
                        "        });\n\t\t";

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


    public Map<String, String> getAllEventsMap(String html) {
        if (html == null)
            return null;
        Document doc = Jsoup.parse(wrapToTemplate(html));
        for (Element loop : doc.getElementsByAttribute("loop")) {
            loop.remove();
        }
        final Elements allElements = doc.getAllElements();
        Map<String, String> map = new HashMap<>();
        for (Element element : allElements) {
            if (element.attributes() == null || element.attributes().size() == 0) continue;
            for (Attribute attribute : element.attributes()) {
                if (attribute.getKey().startsWith("on-")) {
                    final String value = attribute.getValue();
                    map.put(value, value);
                }
            }
            if (element.hasAttr("bind")) {
                final String valueAttribute = element.attributes().get("value");
                final String checkedAttribute = element.attributes().get("checked");
                if (valueAttribute.startsWith("{{")) {
                    if (element.tagName().equals("input")) {
                        importsSet.add("import elemental2.dom.HTMLInputElement;\n");
                        final String between = Util.getBetween(valueAttribute);
                        map.put("input:" + between, between);
                    } else if (element.tagName().equals("textarea")) {
                        importsSet.add("import elemental2.dom.HTMLTextAreaElement;\n");
                        final String between = Util.getBetween(valueAttribute);
                        map.put("textarea:" + between, between);
                    } else if (element.tagName().equals("select")) {
                        //todo
                    }
                } else if (checkedAttribute.startsWith("{{")) {
                    if (element.tagName().equals("input") && (element.attributes().get("type").equals("checkbox")
                            || element.attributes().get("type").equals("radio"))) {
                        importsSet.add("import elemental2.dom.HTMLInputElement;\n");
                        final String between = Util.getBetween(checkedAttribute);
                        map.put("radio:" + between, between);
                    }
                }
            }

        }
        return map;
    }

    private String wrapToTemplate(String html) {
        String res = "<template>" + html + "</template>";
        return res;
    }

    public String getImports() {
        return importsSet.stream().collect(Collectors.joining());
    }

    public static boolean isBinder(String key) {
        return (key.startsWith("input:")
                || key.startsWith("radio:")
                || key.startsWith("textarea:"));
    }

    public static String getBinderAssign(String str) {
        if (str.startsWith("input:")) {
            return "=((HTMLInputElement) e.target).value;";

        } else if (str.startsWith("radio:")) {
            return "=((HTMLInputElement) e.target).checked;";

        } else if (str.startsWith("textarea:")) {
            return "=((HTMLTextAreaElement) e.target).value;";
        }
        return "";
    }


}
