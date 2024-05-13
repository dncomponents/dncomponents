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

//language=html
@Component(template = "<div class='helloCss'>\n" +
                      "<h2>*** HelloComponent ***</h2>\n" +
                      "  <h1>Hello, {{name}}!</h1>\n" +
                      "</div>\n",
//language=css
        css = ".helloCss{\n" +
              "           background: #1dea16;\n" +
              "           padding: 20px;\n" +
              "           border: 1px solid gray;\n" +
              "         }\n",
        tag = "hello-component"
)

public class HelloComponent implements IsElement {
    HtmlBinder<HelloComponent> binder = HtmlBinder.create(HelloComponent.class, this);

    String name = "All";

    public HelloComponent() {
        binder.bindAndUpdateUi();
    }

    @Override
    public HTMLElement asElement() {
        return binder.getRoot();
    }
}