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
import com.dncomponents.client.main.testing.TestingHelper;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.List;

//language=html
@Component(template = "<div>\n" +
                      "    <h2>Loop in table</h2>\n" +
                      "    <table>\n" +
                      "        <thead>\n" +
                      "        <tr>\n" +
                      "            <th>Name</th>\n" +
                      "            <th>Age</th>\n" +
                      "        </tr>\n" +
                      "        </thead>\n" +
                      "        <tbody>\n" +
                      "        <template dn-loop='person in persons'>\n" +
                      "            <tr>\n" +
                      "                <td>\n" +
                      "                    {{person.getName()}}\n" +
                      "                </td>\n" +
                      "                <td>\n" +
                      "                    {{person.getAge()}}\n" +
                      "                </td>\n" +
                      "            </tr>\n" +
                      "        </template>\n" +
                      "        </tbody>\n" +
                      "    </table>\n" +
                      "    <h2>Loop in list</h2>\n" +
                      "    <ul dn-loop='person 1in persons'>\n" +
                      "        <li>{{person.getName()}}</li>\n" +
                      "    </ul>\n" +
                      "    <h2>Loop components in list</h2>\n" +
                      "    <ul dn-loop='person in persons'>\n" +
                      "        <PersonComponent person='{{person}}' color='red'></PersonComponent>\n" +
                      "    </ul>\n" +
                      "</div>\n")
public class LoopComponent implements IsElement {
    HtmlBinder<LoopComponent> binder = HtmlBinder.create(LoopComponent.class, this);
    List<Person> persons = TestingHelper.getPeople(10);

    public LoopComponent() {
        binder.bindAndUpdateUi();
    }

    @Override
    public HTMLElement asElement() {
        return binder.getRoot();
    }
}