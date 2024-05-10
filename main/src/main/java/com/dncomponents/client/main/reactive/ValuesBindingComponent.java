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
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


//language=html
@Component(template = "          <div>\n" +
                      "            <p>Name: {{name}}</p>\n" +
                      "            <p>Surname: {{surname}}</p>\n" +
                      "            <p>Age: {{age}}</p>\n" +
                      "            <b>{{fullNameAndAge()}}</b>\n" +
                      "          </div>\n")
public class ValuesBindingComponent implements IsElement {
    HtmlBinder<ValuesBindingComponent> binder = HtmlBinder.create(ValuesBindingComponent.class, this);


    String name = "John";
    String surname = "Doe";
    int age = 33;

    String fullNameAndAge() {
        return name + ", " + surname + " - " + age;
    }

    public ValuesBindingComponent() {
        binder.bindAndUpdateUi();
    }

    @Override
    public HTMLElement asElement() {
        return binder.getRoot();
    }

}