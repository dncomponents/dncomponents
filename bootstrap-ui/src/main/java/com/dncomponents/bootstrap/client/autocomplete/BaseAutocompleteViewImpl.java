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

package com.dncomponents.bootstrap.client.autocomplete;

import com.dncomponents.UiField;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.views.core.ui.autocomplete.BaseAutocompleteView;
import elemental2.dom.CustomEvent;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public abstract class BaseAutocompleteViewImpl<T> implements BaseAutocompleteView<T> {

    @UiField
    protected HTMLElement root;
    @UiField
    protected TextBox textBox;
    @UiField
    protected HTMLElement listPanel;
    @UiField
    protected Button button;
    @UiField
    protected HTMLElement buttonText;

    @Override
    public void addKeyUpHandler(KeyUpHandler keyUpHandler) {
        textBox.addHandler(keyUpHandler);
    }

    @Override
    public HandlerRegistration addButtonClickHandler(ClickHandler clickHandler) {
        return button.addClickHandler(clickHandler);
    }

    @Override
    public void setStringValue(String value) {
        if (buttonText != null) {
            if (value == null)
                value = "Choose...";
            buttonText.innerHTML = value;
        }
    }

    @Override
    public void showListPanel(boolean b, Command done) {
        if (b)
            listPanel.style.display = "block";
        else {
            listPanel.style.display = "none";
        }
        if (done != null) done.execute();
    }

    @Override
    public void setTextBoxFocused(boolean b) {
        if (b)
            textBox.asElement().focus();
        else
            textBox.asElement().blur();
    }

    @Override
    public String getTextBoxCurrentValue() {
        return textBox.getValueFromView();
    }

    @Override
    public void setTextBoxCurrentValue(String value) {
        textBox.setValue(null, true);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public void fireEvent(CustomEvent event) {
        asElement().dispatchEvent(event);
    }
}
