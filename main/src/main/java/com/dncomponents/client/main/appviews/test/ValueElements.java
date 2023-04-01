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

package com.dncomponents.client.main.appviews.test;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.State;
import com.dncomponents.client.dom.handlers.Handlers;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


@UiTemplate("" +
        "<div ui-field='root'>\n" +
        "    <span>{{someValue}}</span>\n" +
        "<span>bbb</span>\n" +
        "    <div>{{someValue}}</div>\n" +
        "    " +
        "<button ui-field='btn'></button>\n" +
        "</div>")
public class ValueElements implements IsElement {
    HtmlBinder binder = HtmlBinder.get(ValueElements.class, this);
    {
        binder.bind();
    }
    @UiField
    HTMLElement root;
    @UiField
    HTMLElement btn;

    State<Integer> someValue = new State("blabal", "someValue", binder);

    int n = 0;

    public ValueElements() {
//        Handlers.addClickHandler(btn, mouseEvent -> {
//            someValue.setValue(n++);
//        });
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

      static ValueElements instance;
    public static ValueElements getInstance() {
        if (instance == null)
            instance = new ValueElements();
        return instance;
    }

}
