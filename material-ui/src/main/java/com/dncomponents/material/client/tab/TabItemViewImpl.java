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

package com.dncomponents.material.client.tab;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.views.core.ui.tab.TabItemView;
import com.dncomponents.client.views.core.ui.tab.TabItemViewSlots;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

public class TabItemViewImpl implements TabItemView {

    @UiField
    HTMLElement tabItemNav;
    @UiField
    HTMLElement tabItemPanel;
    @UiField
    HTMLElement tabItemContent;
    @UiField
    HTMLElement indicator;
    @UiField
    HTMLElement iconPanel;
    @UiField
    String showTabContentStyle;
    @UiField
    String active;
    @UiField
    String activeIndicator;


    HtmlBinder uiBinder = HtmlBinder.create(TabItemViewImpl.class, this);

    public TabItemViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    public TabItemViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }


    @Override
    public void addItemSelectedHandler(EventListener handler) {
        tabItemNav.addEventListener("click", handler);
    }

    @Override
    public void select(boolean b) {
        if (b) {
            tabItemNav.classList.add(active);
            indicator.classList.add(activeIndicator);
            tabItemContent.classList.add(showTabContentStyle);
        } else {
            tabItemNav.classList.remove(active);
            indicator.classList.remove(activeIndicator);
            tabItemContent.classList.remove(showTabContentStyle);
        }
    }

    @Override
    public void setItemTitleHtml(String html) {
        tabItemPanel.innerHTML = html;
    }

    @Override
    public void setItemTitle(String text) {
        tabItemPanel.textContent = text;
    }

    @Override
    public void setItemTitle(HTMLElement html) {
        tabItemPanel.innerHTML = html.innerHTML;
    }

    @Override
    public void setItemContent(String html) {
        tabItemContent.textContent = html;
    }

    @Override
    public void setItemContent(HTMLElement htmlElement) {
        tabItemContent.innerHTML = htmlElement.innerHTML;
    }

    @Override
    public void setImmediate(Command command) {

    }

    @Override
    public HTMLElement getTabItemNav() {
        return tabItemNav;
    }

    @Override
    public HTMLElement getTabItemContent() {
        return tabItemContent;
    }

    TabItemViewSlots tabItemViewSlots = new TabItemViewSlots() {
        @Override
        public HTMLElement getTitle() {
            return tabItemPanel;
        }

        @Override
        public HTMLElement getIcon() {
            return iconPanel;
        }

        @Override
        public HTMLElement getContent() {
            return tabItemContent;
        }
    };

    @Override
    public TabItemViewSlots getViewSlots() {
        return tabItemViewSlots;
    }


    @Override
    public HTMLElement asElement() {
        return tabItemPanel;
    }
}
