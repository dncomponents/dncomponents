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

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nikolasavic
 */
public class ValuesProcessing {

    private Set<String> valuesNames = new HashSet<>();
    private String imports = "";
    String updates = "";

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

        List<String> allStateNames = findSubstrings(doc.html());
        if (allStateNames != null) {
            valuesNames = allStateNames.stream().collect(Collectors.toSet());
        }

        for (String valuesName : valuesNames) {
            valuesName = valuesName.replace("&quot;", "\"");
            updates += "        template.addStateFunction(\"" + StringEscapeUtils.escapeJava(valuesName) + "\",()->" +
                    Util.createJavaCode(valuesName, null, false) + ");\n";
        }
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

    public String getUpdates() {
        return updates;
    }

    public String getImports() {
        return imports;
    }
}
