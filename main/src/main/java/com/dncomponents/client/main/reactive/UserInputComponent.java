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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//language=html
@Component(template = "<div>\n" +
                      "\n" +
                      "    <h2>*** input and two-way binding ***</h2>\n" +
                      "\n" +
                      "\n" +
                      "    <h2>Text Input</h2>\n" +
                      "    <input dn-model=\"text\">\n" +
                      "    <p>{{text}}</p>\n" +
                      "    <h2>Checkbox</h2>\n" +
                      "    <input type=\"checkbox\" id=\"checkbox\" dn-model=\"checked\">\n" +
                      "    <label for=\"checkbox\">Checked: {{checked}}</label>\n" +
                      "\n" +
                      "    <h2>Multi Checkbox</h2>\n" +
                      "    <input type=\"checkbox\" id=\"jack\" value=\"Jack\" dn-model=\"checkedNames\">\n" +
                      "    <label for=\"jack\">Jack</label>\n" +
                      "    <input type=\"checkbox\" id=\"john\" value=\"John\" dn-model=\"checkedNames\">\n" +
                      "    <label for=\"john\">John</label>\n" +
                      "    <input type=\"checkbox\" id=\"mike\" value=\"Mike\" dn-model=\"checkedNames\">\n" +
                      "    <label for=\"mike\">Mike</label>\n" +
                      "    <p>Checked names:{{text}} and {{checkedNames}} and {{selected}} and {{week}} and {{text}}</p>\n" +
                      "\n" +
                      "    <h2>Radio</h2>\n" +
                      "    <input type=\"radio\" id=\"one\" value=\"One\" dn-model=\"picked\">\n" +
                      "    <label for=\"one\">One</label>\n" +
                      "    <br>\n" +
                      "    <input type=\"radio\" id=\"two\" value=\"Two\" dn-model=\"picked\">\n" +
                      "    <label for=\"two\">Two</label>\n" +
                      "    <p>Picked: {{picked}}</p>\n" +
                      "\n" +
                      "    <h2>Select</h2>\n" +
                      "    <select dn-model=\"selected\">\n" +
                      "        <option disabled value=\"\">Please select one</option>\n" +
                      "        <option>A</option>\n" +
                      "        <option>B</option>\n" +
                      "        <option>C</option>\n" +
                      "    </select>\n" +
                      "    <p>Selected: {{selected}}</p>\n" +
                      "\n" +
                      "    <h2>Multi Select</h2>\n" +
                      "    <select dn-model=\"multiSelected\" multiple style=\"width:100px\">\n" +
                      "        <option>A</option>\n" +
                      "        <option>B</option>\n" +
                      "        <option>C</option>\n" +
                      "    </select>\n" +
                      "    <p>Selected: {{multiSelected}}</p>\n" +
                      "    </template>\n" +
                      "\n" +
                      "    <h2>Different value examples</h2>\n" +
                      "    <label for=\"week\">What week would you like to start?</label>\n" +
                      "    <input id=\"week\" type=\"week\" name=\"week\" dn-model=\"week\"/> <br>\n" +
                      "    <label for=\"week\">Change week value in text box: </label>\n" +
                      "    <input type='text' dn-model=\"week\">\n" +
                      "    <p>Selected week: {{week}}</p>\n" +
                      "    <h2>Select single</h2>\n" +
                      "    <input type='text' dn-model=\"selected\">\n" +
                      "    <select dn-model=\"selected\">\n" +
                      "        <option disabled value=\"\">Please select one</option>\n" +
                      "        <option>A</option>\n" +
                      "        <option>B</option>\n" +
                      "        <option>C</option>\n" +
                      "    </select>\n" +
                      "    <p>Selected: {{selected}}</p>\n" +
                      "    <h2>Select multiple</h2>\n" +
                      "    <button dn-on-click='addFruit(\"Orange\")'>Add/remove Orange</button>\n" +
                      "    <button dn-on-click='addFruit(\"Grape\")'>Add/remove Grape</button>\n" +
                      "    <select dn-model=\"selectedFruits\" multiple>\n" +
                      "        <option value=\"apple\">Apple</option>\n" +
                      "        <option value=\"banana\">Banana</option>\n" +
                      "        <option value=\"orange\">Orange</option>\n" +
                      "        <option value=\"grape\">Grape</option>\n" +
                      "        <option value=\"strawberry\">Strawberry</option>\n" +
                      "    </select>\n" +
                      "    <p>Selected fruits: </p>\n" +
                      "    <template dn-loop='fruit in selectedFruits'>\n" +
                      "        <b>{{fruit}}</b>\n" +
                      "    </template>\n" +
                      "    <div dn-loop='fruit in selectedFruits'>\n" +
                      "        <b>{{fruit}}</b>\n" +
                      "    </div>\n" +
                      "</div>\n" +
                      "              ")
public class UserInputComponent implements IsElement {
    String value = "Type";
    String selected = "A";
    List<String> selectedFruits = new ArrayList<>(Arrays.asList("Banana"));
    List<String> checkedNames = new ArrayList<>();
    List<String> multiSelected = new ArrayList<>();
    String text = "message";
    String week = "2024-W33";
    boolean checked = true;
    String picked = "One";

    void addFruit(String fruit) {
        if (!selectedFruits.contains(fruit)) {
            selectedFruits.add(fruit);
        } else {
            selectedFruits.remove(fruit);
        }
        binder.updateUi();
    }

    HtmlBinder<UserInputComponent> binder = HtmlBinder.create(UserInputComponent.class, this);

    public UserInputComponent() {
        binder.bindAndUpdateUi();
    }

    @Override
    public HTMLElement asElement() {
        return binder.getRoot();
    }
}