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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;


public class IfsProcessing {

    private Set<String> valuesNames = new HashSet<>();
    private Set<String> updateSet;


    public IfsProcessing(String html, Set<String> updateSet, Set<String> valuesNames) {
        this.updateSet = updateSet;
        this.valuesNames = valuesNames;
        parse(html);
    }

    public void parse(String html) {
        if (html == null)
            return;
        Document doc = Jsoup.parse(html);
        final Elements dloop = doc.getElementsByAttribute("dn-loop");
        for (org.jsoup.nodes.Element element : dloop) {
            element.remove();
        }


        // Set to store attribute values

        // Find all elements with "dn-if", "dn-else", and "dn-else-if" attributes
        Elements elements = doc.select("[dn-if], [dn-else], [dn-else-if]");

        // Iterate through elements and extract attribute values
        for (Element element : elements) {
            // Extract "dn-if" attribute value
            String dnIfValue = element.attr("dn-if");
            if (!dnIfValue.isEmpty()) {
                valuesNames.add(dnIfValue);
            }

            // Extract "dn-else" attribute value
            String dnElseValue = element.attr("dn-else");
            if (!dnElseValue.isEmpty()) {
                valuesNames.add(dnElseValue);
            }

            // Extract "dn-else-if" attribute value
            String dnElseIfValue = element.attr("dn-else-if");
            if (!dnElseIfValue.isEmpty()) {
                valuesNames.add(dnElseIfValue);
            }
        }
        for (String valuesName : valuesNames) {
            valuesName = valuesName.replace("&quot;", "\"");
            updateSet.add("        template.addStateFunction(\"" + StringEscapeUtils.escapeJava(valuesName) + "\", () -> " +
                          Util.createJavaCode(valuesName, null, false) + ");\n");
        }

    }

}