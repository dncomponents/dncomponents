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

package com.dncomponents.client.main.components.appviews.modal;

import com.dncomponents.UiField;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.modal.Dialog;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

public class ModalAppView implements IsElement {
    @UiField
    HTMLElement root;
    @UiField
    Button showDialogBtn;
    @UiField
    public Dialog dialog;
    @UiField
    public Button closeBtn;
    @UiField
    public TextArea ta;
    @UiField(provided = true)
    public Dialog eventsDialog = new Dialog();
    @UiField
    public Button eventsButton;
    @UiField
    public Button rendererButton;
    @UiField(provided = true)
    public Dialog<Fruit> rendererDialog = new Dialog<Fruit>();
    @UiField
    public CheckBox backDropChBox;
    @UiField
    public CheckBox draggableChBox;
    @UiField
    public CheckBox closeOnEscapeChBox;

    public ModalAppView() {
        HtmlBinder.create(ModalAppView.class, this).bind();
        initRenderer();
        initEvents();
        dialog.setWidth("800px");
        dialog.setHeight("200px");
        backDropChBox.setValue(dialog.isBackdrop());
        draggableChBox.setValue(dialog.isDraggable());
        closeOnEscapeChBox.setValue(dialog.isCloseOnEscape());
        backDropChBox.addValueChangeHandler((ValueChangeEvent event) -> dialog.setBackdrop((Boolean) event.getValue()));
        draggableChBox.addValueChangeHandler(event -> dialog.setDraggable((Boolean) event.getValue()));
        closeOnEscapeChBox.addValueChangeHandler(event -> dialog.setCloseOnEscape((Boolean) event.getValue()));
        showDialogBtn.addClickHandler(mouseEvent -> dialog.show());
        closeBtn.addClickHandler(e -> dialog.hide());
    }

    private void initEvents() {
        eventsDialog.setHeader(e -> e.textContent = "Header");
        eventsDialog.setFooter(e -> e.textContent = "Footer");
        eventsDialog.setContent(e -> e.textContent = "Content");

        eventsButton.addClickHandler(e -> eventsDialog.show());
        eventsDialog.addHideHandler(event -> ta.append("hideEvent\n"));
        eventsDialog.addShowHandler(event -> ta.append("showEvent\n"));
    }

    private void initRenderer() {
        rendererDialog.setRenderer((userObject, slots) -> {
            slots.getHeaderPanel().innerHTML = "<b>" + userObject.getName() + "</b>";
            slots.getContentPanel().innerHTML = "<b>" + userObject.getDescription() + "</b>";
        });
        rendererDialog.setFooter(e -> e.appendChild(new Button("Save").asElement()));
        rendererDialog.setUserObject(Fruit.fruits.get(3));
        rendererButton.addClickHandler(e -> rendererDialog.show());
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    static ModalAppView instance;

    public static ModalAppView getInstance() {
        if (instance == null)
            instance = new ModalAppView();
        return instance;
    }
}
