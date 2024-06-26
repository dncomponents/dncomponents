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

package com.dncomponents.material.client.dialog;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.modal.DialogView;
import com.dncomponents.client.views.core.ui.modal.DialogViewSlots;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;


public class DialogViewImpl implements DialogView {

    public static final String VIEW_ID = "default";
    @UiField
    public HTMLElement root;
    @UiField
    public HTMLElement header;
    @UiField
    public HTMLElement body;
    @UiField
    public HTMLElement footer;
    @UiField
    public HTMLElement closeButton;
    @UiField
    public HTMLElement titleHeader;
    @UiField
    String showDialogStyle;

    HtmlBinder uiBinder = HtmlBinder.create(DialogViewImpl.class, this);

    public DialogViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    public DialogViewImpl(String templateContent) {
        uiBinder.setTemplateContent(templateContent);
        uiBinder.bind();
    }


    @Override
    public void setHeader(IsElement element) {
        header.innerHTML = "";
        header.appendChild(element.asElement());
    }

    @Override
    public void setContent(HTMLElement element) {
        body.innerHTML = "";
        body.appendChild(element);
    }

    @Override
    public void setTitle(String title) {
        titleHeader.innerHTML = title;
    }

    @Override
    public void setFooter(IsElement el) {
        footer.innerHTML = "";
        footer.appendChild(el.asElement());
    }

    @Override
    public void addOkHandler(ClickHandler clickHandler, String text) {

    }

    @Override
    public void addCloseHandler(Command onCloseCmd) {
        final ClickHandler clkHandler = mouseEvent -> {
            mouseEvent.stopImmediatePropagation();
            onCloseCmd.execute();
        };
        DomUtil.addHandler(closeButton, clkHandler);
    }

    @Override
    public void show() {
        root.classList.add(showDialogStyle);
    }

    @Override
    public void hide() {
        root.classList.remove(showDialogStyle);
    }

    @Override
    public void addFooterElement(HTMLElement element) {
        footer.appendChild(element);
    }

    @Override
    public void clearFooter() {
        footer.innerHTML = "";
    }

    @Override
    public void setWidth(String width) {

    }

    @Override
    public void setHeight(String height) {

    }

    @Override
    public void setBackDrop(boolean backdrop) {

    }

    @Override
    public void setCloseOnEscape(boolean closeOnEscape) {

    }

    @Override
    public void setDraggable(boolean draggable) {

    }

    @Override
    public void setPosition(int top, int left) {

    }

    @Override
    public int getTopPosition() {
        return 0;
    }

    @Override
    public int getLeftPosition() {
        return 0;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }


    @Override
    public DialogViewSlots getViewSlots() {
        return slots;
    }

    DialogViewSlots slots = new DialogViewSlots() {
        @Override
        public HTMLElement getHeaderPanel() {
            return header;
        }

        @Override
        public HTMLElement getContentPanel() {
            return body;
        }

        @Override
        public HTMLElement getFooterPanel() {
            return footer;
        }
    };
}
