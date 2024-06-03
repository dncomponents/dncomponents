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

import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class EventsProcessing {
    private Set<String> importsSet = new HashSet<>();
    private Set<String> updateSet;
    private Set<String> eventsSet;
    private final boolean loop;

    public EventsProcessing(String html, Set<String> updateSet, Set<String> eventsSet, boolean loop) {
        this.updateSet = updateSet;
        this.eventsSet = eventsSet;
        this.loop = loop;
        parse(html);
    }


    public void parse(String html) {
        if (html == null)
            return;
        final Map<String, String> allEventsMap = getAllEventsMap(html);

        for (Map.Entry<String, String> entry : allEventsMap.entrySet()) {
            setEventStringHandler(entry.getKey(), entry.getValue(), eventsSet);
        }
    }

    public static void setEventStringHandler(String key, String value, Set<String> eventsSet) {
        if (isModel(key)) {
            eventsSet.add("template.addEventHandler(\"" + key + "\", e -> {\n\t\t\t" +
                          getModelAssign(key, value, false) +
                          "\n\t\t});\n\t\t");
        } else {
            eventsSet.add("template.addEventHandler(\"" + StringEscapeUtils.escapeJava(key) + "\", e -> {\n\t\t\t" +
                          Util.createJavaCode(value, null, true) +
                          "\n\t\t});\n\t\t");
        }
    }

    public Map<String, String> getAllEventsMap(String html) {
        if (html == null)
            return null;
        Document doc = Jsoup.parse(wrapToTemplate(html));
        for (Element loop : doc.getElementsByAttribute("dn-loop")) {
            loop.remove();
        }

        final Elements allElements = doc.getAllElements();
        Map<String, String> map = new HashMap<>();
        for (Element element : allElements) {
            if (element.attributes() == null || element.attributes().size() == 0) continue;
            for (Attribute attribute : element.attributes()) {
                if (attribute.getKey().startsWith("dn-on-")) {
                    final String value = attribute.getValue();
                    map.put(value, value);
                }
            }
            if (element.hasAttr("dn-model")) {
                final String model = element.attributes().get("dn-model");

                if (!loop)
                    updateSet.add("        template.addStateFunction(\"" + model + "\", () -> d." + model + ");\n");

                if (element.tagName().equals("textarea")) {
                    importsSet.add("import elemental2.dom.HTMLTextAreaElement;\n");
                    map.put("textarea:" + model, model);
                }
                if (element.tagName().equals("input") && (element.attributes().get("type").equals("checkbox")
                                                          || element.attributes().get("type").equals("radio"))) {
                    importsSet.add("import elemental2.dom.HTMLInputElement;\n");
                    map.put("radio:" + model, model);
                } else if (element.tagName().equals("input")) {
                    importsSet.add("import elemental2.dom.HTMLInputElement;\n");
                    map.put("input:" + model, model);
                }

                if (element.tagName().equals("select")) {
                    importsSet.add("import com.dncomponents.client.dom.DomUtil;\n");
                    map.put("select:" + model, model);
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


    public static boolean isModel(String key) {
        return (key.startsWith("input:")
                || key.startsWith("radio:")
                || key.startsWith("textarea:")
                || key.startsWith("select:"));
    }


    public static String getModelAssign(String str, String value, boolean loop) {
        String dValue = loop ? value : Util.createJavaCode(value, null, true).replace(";", "");
        if (str.startsWith("input:")) {
            return dValue + "=((HTMLInputElement) e.target).value;";
        } else if (str.startsWith("radio:")) {
            return dValue + "=com.dncomponents.client.dom.DomUtil.checkBoxSelection(" + dValue + ",e);";
        } else if (str.startsWith("textarea:")) {
            return dValue + "=((HTMLTextAreaElement) e.target).value;";
        } else if (str.startsWith("select:")) {
            return dValue + "=com.dncomponents.client.dom.DomUtil.getSelection(e.target);";
        }
        return "";
    }


}
