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
    private Map<String, String> fieldsAndTypesMap;

    public EventsProcessing(Map<String, String> fieldsAndTypesMap, Set<String> updateSet) {
        this.fieldsAndTypesMap = fieldsAndTypesMap;
        this.updateSet = updateSet;
    }


    public String parse(String html) {
        if (html == null)
            return "";
        final Map<String, String> allEventsMap = getAllEventsMap(html);
        String generated = "";
        for (Map.Entry<String, String> entry : allEventsMap.entrySet()) {
            if (isBinder(entry.getKey())) {
                generated += "template.addEventHandler(\"" + entry.getKey() + "\", e -> {\n" +
                             getBinderAssign(entry.getKey(), entry.getValue(), fieldsAndTypesMap) +
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
//                final String modelAttribute = element.attributes().get("dn-model");
                final String model = element.attributes().get("dn-model");

                updateSet.add("        template.addStateFunction(\"" + model + "\", () -> d." + model + ");\n");

//                final String model = Util.getBetween(modelAttribute);

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


    public static boolean isBinder(String key) {
        return (key.startsWith("input:")
                || key.startsWith("radio:")
                || key.startsWith("textarea:")
                || key.startsWith("select:"));
    }


    //todo check loop
    public static String getBinderAssign(String str, String value, Map<String, String> fieldsAndTypesMap) {
        String dValue = Util.createJavaCode(value, null, true).replace(";", "");
        if (str.startsWith("input:")) {
            return dValue + "=((HTMLInputElement) e.target).value;";
        } else if (str.startsWith("radio:")) {
            if (fieldsAndTypesMap.get(value) != null) {
                if (fieldsAndTypesMap.get(value).equals("boolean")) {
                    return dValue + "=((HTMLInputElement) e.target).checked;";
                } else if (fieldsAndTypesMap.get(value).equals("collection")) {
                    return "DomUtil.checkBoxSelection(e.target, " + dValue + ");";
                }
            } else {
                return dValue + "=((HTMLInputElement) e.target).value;";
            }
        } else if (str.startsWith("textarea:")) {
            return dValue + "=((HTMLTextAreaElement) e.target).value;";
        } else if (str.startsWith("select:")) {
            return dValue + "=DomUtil.getSelection(e.target);";
        }
        return "";
    }


}
