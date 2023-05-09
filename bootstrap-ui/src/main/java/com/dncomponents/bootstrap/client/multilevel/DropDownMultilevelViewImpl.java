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

package com.dncomponents.bootstrap.client.multilevel;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.dropdown.DropDownView;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class DropDownMultilevelViewImpl implements DropDownView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement button;
    @UiField
    HTMLElement dropDownMenu;
    @UiField
    String dropDownMenuShowStyle;

    HtmlBinder uiBinder = HtmlBinder.create(DropDownMultilevelViewImpl.class, this);

    public DropDownMultilevelViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public DropDownMultilevelViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void addClickOnButton(BaseEventListener handler) {
        handler.addTo(button);
    }

    @Override
    public void showList(boolean b) {
        if (b)
            dropDownMenu.classList.add(dropDownMenuShowStyle);
        else
            dropDownMenu.classList.remove(dropDownMenuShowStyle);
    }

    @Override
    public void addItem(IsElement item) {
        dropDownMenu.appendChild(item.asElement());
    }

    @Override
    public void removeItem(IsElement item) {
        item.asElement().remove();
    }

    @Override
    public void setButtonHtmlContent(HTMLElement content) {
        button.innerHTML = "";
        button.appendChild(content);
    }

    @Override
    public IsElement getRelativeElement() {
        return () -> button;
    }

    @Override
    public void setButtonContent(String content) {
        button.innerHTML = content;
    }

    @Override
    public HandlerRegistration addClickOutOfButton(ClickHandler clickHandler) {
        return clickHandler.addTo(DomGlobal.document.body);
    }

    @Override
    public void addDropDownPanel(IsElement dropDownPanel) {
        root.appendChild(dropDownPanel.asElement());
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
