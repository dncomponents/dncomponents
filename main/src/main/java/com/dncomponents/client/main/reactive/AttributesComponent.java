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

package com.dncomponents.client.main.reactive;

import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.main.testing.Person;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

@Component(
//language=html
        template = "<div class='someCss'>\n" +
                   "    <h2>Testing component attributes</h2>\n" +
                   "    <PersonComponent person='{{pers}}' someNumb='{{number}}' color='red'></PersonComponent>\n" +
                   "</div>\n",
//language=css
        css = "        .someCss{\n" +
              "          background:    #2ce3c1;\n" +
              "        }\n"
)
public class AttributesComponent implements IsElement {
    HtmlBinder<AttributesComponent> binder;

    Person pers;
    int number = 200;

    public AttributesComponent() {
        pers = new Person("Peter");
        pers.setAge(32);
        pers.setGender("Male");
        binder = HtmlBinder.create(AttributesComponent.class, this);
        binder.bindAndUpdateUi();
    }

    @Override
    public HTMLElement asElement() {
        return binder.getRoot();
    }

}