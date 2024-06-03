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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//language=html
@Component(template = "<div>\n" +
                      "    <h1 class='main-title'>Main App</h1>\n" +
                      "    <select dn-model=\"currentScreens\" multiple style='height: 140px;'>\n" +
                      "        <option>TodoComponent</option>\n" +
                      "         <option>UserInputComponent</option>\n" +
                      "        <option>EventsComponent</option>\n" +
                      "        <option>AttributesComponent</option>\n" +
                      "        <option>LoopComponent</option>\n" +
                      "        <option>ValuesBindingComponent</option>\n" +
                      "        <option>ConditionalComponent</option>\n" +
                      "        <option>HelloComponent</option>\n" +
                      "    </select>\n" +
                      "    <hello-component dn-if='currentScreens.contains(\"HelloComponent\")'></hello-component>\n" +
                      "    <UserInputComponent dn-if='currentScreens.contains(\"UserInputComponent\")'></UserInputComponent>\n" +
                      "    <AttributesComponent dn-if='currentScreens.contains(\"AttributesComponent\")'></AttributesComponent>\n" +
                      "    <EventsComponent dn-if='currentScreens.contains(\"EventsComponent\")'></EventsComponent>\n" +
                      "    <LoopComponent dn-if='currentScreens.contains(\"LoopComponent\")'></LoopComponent>\n" +
                      "    <ValuesBindingComponent dn-if='currentScreens.contains(\"ValuesBindingComponent\")'></ValuesBindingComponent>\n" +
                      "    <ConditionalComponent dn-if='currentScreens.contains(\"ConditionalComponent\")'></ConditionalComponent>\n" +
                      "    <TodoComponent dn-if='currentScreens.contains(\"TodoComponent\")'></TodoComponent>\n" +
                      "</div>\n",
        //language=css
        css = ".main-title{\n" +
              "    margin-top: 10px;\n" +
              "    margin-bottom: 10px;\n" +
              "}\n"
)
public class MainApp implements IsElement {
    HtmlBinder<MainApp> binder = HtmlBinder.create(MainApp.class, this);
    List<String> currentScreens = new ArrayList<>(Arrays.asList("TodoComponent"));

    public MainApp() {
        binder.bindAndUpdateUi();
    }

    @Override
    public HTMLElement asElement() {
        return binder.getRoot();
    }
}