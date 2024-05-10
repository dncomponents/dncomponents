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
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.views.core.ui.accordion.AccordionItemView;
import com.dncomponents.client.views.core.ui.accordion.AccordionItemViewSlots;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

public class AccordionItemViewImpl implements AccordionItemView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement accordionItemTitle;
    @UiField
    HTMLElement accordionItemTitlePanel;
    @UiField
    HTMLElement contentPanel;
    @UiField
    HTMLElement contentPanelParent;
    @UiField
    String showContentStyle;


    HtmlBinder uiBinder = HtmlBinder.create(AccordionItemViewImpl.class, this);

    public AccordionItemViewImpl(String templateContent) {
        uiBinder.setTemplateContent(templateContent);
        uiBinder.bind();
    }


    public AccordionItemViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public void addItemSelectedHandler(EventListener handler) {
        accordionItemTitlePanel.addEventListener("click", handler);
    }

    @Override
    public void select(boolean b) {
        if (b)
            contentPanelParent.classList.add(showContentStyle);
        else
            contentPanelParent.classList.remove(showContentStyle);
    }

    @Override
    public void setItemTitle(String html) {
        accordionItemTitle.innerHTML = "";
        accordionItemTitle.innerHTML = html;
    }

    @Override
    public void setItemTitle(HTMLElement html) {
        accordionItemTitle.innerHTML = "";
        accordionItemTitle.appendChild(html);
    }

    @Override
    public void setItemContent(String html) {
        contentPanel.innerHTML = "";
        contentPanel.innerHTML = html;
    }

    @Override
    public void setItemContent(HTMLElement htmlElement) {
        contentPanel.innerHTML = htmlElement.innerHTML;
        contentPanel.innerHTML = htmlElement.innerHTML;
    }

    @Override
    public void setImmediate(Command command) {

    }

    @Override
    public String getTitle() {
        return accordionItemTitle.innerHTML;
    }

    @Override
    public String getContent() {
        return contentPanel.innerHTML;
    }

    @Override
    public AccordionItemViewSlots getViewSlots() {
        return accordionItemViewSlots;
    }

    private AccordionItemViewSlots accordionItemViewSlots = new AccordionItemViewSlots() {
        @Override
        public HTMLElement getTitle() {
            return accordionItemTitle;
        }

        @Override
        public HTMLElement getContent() {
            return contentPanel;
        }
    };


    @Override
    public HTMLElement asElement() {
        return root;
    }
}
