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

package com.dncomponents.bootstrap.client.accordion;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.accordion.AccordionItemView;
import com.dncomponents.client.views.core.ui.accordion.AccordionUi;
import com.dncomponents.client.views.core.ui.accordion.AccordionView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class AccordionUiImpl implements AccordionUi {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLTemplateElement accordion;
    @UiField
    HTMLTemplateElement accordionItem;

    AccordionView accordionView;

    HtmlBinder uiBinder = HtmlBinder.create(AccordionUiImpl.class, this);

    public AccordionUiImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public AccordionUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public AccordionItemView getAccordionItemView() {
        return new AccordionItemViewImpl(accordionItem);
    }

    @Override
    public AccordionView getRootView() {
        if (accordionView == null) {
            accordionView = new AccordionViewImpl(accordion);
        }
        return accordionView;
    }
}
