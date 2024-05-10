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
import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Event;
import elemental2.dom.HTMLElement;

import java.util.Arrays;
import java.util.List;

//language=html
@Component(template = "<div class='eventsClass'>\n" +
                      "    <h2 ui-field='title'>Events component</h2>\n" +
                      "    <h2 style='color: {{currentColor}}'>Current Color</h2>\n" +
                      "    <p>Click or hover rectangular to change its color</p>\n" +
                      "    <div dn-on-click-u='changeColor(e)' dn-on-mouseover-u='changeColor(e)'\n" +
                      "         style='padding: 30px;width:40px;background: {{currentColor}};cursor:pointer;'>\n" +
                      "    </div>\n" +
                      "</div>\n",
//language=css
        css = ".eventsClass {\n" +
              "    background: lightgray;\n" +
              "}\n"
)
public class EventsComponent implements IsElement {
    @UiField
    HTMLElement title;
    HtmlBinder<EventsComponent> binder = HtmlBinder.create(EventsComponent.class, this);
    String currentColor = "green";
    int currentIndex = 0;
    List<String> colors = Arrays.asList("red", "blue", "yellow", "green", "pink", "black", "gray", "orange");


    public EventsComponent() {
        binder.bindAndUpdateUi();
    }

    void changeColor(Event event) {
        currentColor = colors.get(currentIndex);
        currentIndex = (currentIndex + 1) % colors.size();
        binder.updateUi();
    }

    @Override
    public HTMLElement asElement() {
        return binder.getRoot();
    }
}
