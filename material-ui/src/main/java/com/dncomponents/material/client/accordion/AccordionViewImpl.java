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

package com.dncomponents.material.client.accordion;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.accordion.AccordionView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;


public class AccordionViewImpl implements AccordionView {

    HtmlBinder uiBinder = HtmlBinder.create(AccordionViewImpl.class, this);

    @UiField
    HTMLElement root;

    public AccordionViewImpl() {
        uiBinder.bind();
    }

    public AccordionViewImpl(String templateContent) {
        uiBinder.setTemplateContent(templateContent);
        uiBinder.bind();
    }

    public AccordionViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public void addItem(IsElement item) {
        root.appendChild(item.asElement());
    }

    public void removeItem(IsElement item) {
        root.removeChild(item.asElement());
    }

    @Override
    public void clearAll() {
        root.innerHTML = "";
    }
}
