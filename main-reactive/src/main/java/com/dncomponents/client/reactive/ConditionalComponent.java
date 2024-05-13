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

package com.dncomponents.client.reactive;

import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

//language=html
@Component(template = "      <div>\n" +
                      "          <h2>Testing conditional rendering</h2>\n" +
                      "          <button dn-on-click='changeType()'>{{type}}</button>\n" +
                      "            <div dn-if='type==\"A\"'>\n" +
                      "                  A block\n" +
                      "              </div>\n" +
                      "              <div dn-else-if='type==\"B\"'>\n" +
                      "                  B block\n" +
                      "              </div>\n" +
                      "              <div dn-else-if='type==\"C\"'>\n" +
                      "                  C block\n" +
                      "              </div>\n" +
                      "              <div dn-else>\n" +
                      "                  Not A/B/C\n" +
                      "              </div>\n" +
                      "       </div>\n",
//language=css
        css = "        .otherCss{\n" +
              "          background: blue;\n" +
              "        }\n"
)
public class ConditionalComponent implements IsElement {
    HtmlBinder<ConditionalComponent> binder = HtmlBinder.create(ConditionalComponent.class, this);
    String type = "B";
    LinkedList<String> types = new LinkedList<>(Arrays.asList("A", "B", "C", "D"));
    Iterator<String> iterator = types.iterator();

    public ConditionalComponent() {
        binder.bindAndUpdateUi();
    }

    void changeType() {
        if (!iterator.hasNext()) {
            iterator = types.listIterator();
        }
        type = iterator.next();
        binder.updateUi();
    }

    @Override
    public HTMLElement asElement() {
        return binder.getRoot();
    }
}