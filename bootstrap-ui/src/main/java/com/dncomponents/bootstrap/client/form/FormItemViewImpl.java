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

package com.dncomponents.bootstrap.client.form;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.views.core.ui.form.FormItemView;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLLabelElement;
import elemental2.dom.HTMLTemplateElement;

public class FormItemViewImpl implements FormItemView {

    @UiField
    public HTMLElement root;
    @UiField
    HTMLLabelElement labelElement;
    @UiField
    HTMLDivElement mainPanel;
    @UiField
    HTMLDivElement helperTextElement;

    HtmlBinder uiBinder = HtmlBinder.get(FormItemViewImpl.class, this);

    public FormItemViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public void setLabelText(String labelText) {
        if (labelText != null)
            this.labelElement.innerHTML = labelText;
    }

    @Override
    public void setError(String error) {
        if (error != null)
            this.helperTextElement.innerHTML = error;
        this.helperTextElement.classList.add("invalid-feedback");
//        this.mainPanel.classList.add("error_form");
        if (error == null) {
            this.helperTextElement.classList.remove("invalid-feedback");
//            this.mainPanel.classList.remove("error_form");
        }
        this.helperTextElement.style.display = "block";
        this.helperTextElement.innerHTML = error;
    }

    @Override
    public void setErrorStyle(boolean b) {
        if (b)
            this.mainPanel.classList.add("errorCell");
        else
            this.mainPanel.classList.remove("errorCell");
    }

    @Override
    public void setSuccessStyle(boolean b) {
        if (b)
            this.mainPanel.classList.add("validCell");
        else
            this.mainPanel.classList.remove("validCell");
    }

    @Override
    public HTMLElement getMainPanel() {
        return this.mainPanel;
    }

    @Override
    public void setHelperText(String helperText) {
        if (helperText != null)
            this.helperTextElement.innerHTML = helperText;
    }

    @Override
    public void setContent(HTMLElement element) {
        mainPanel.appendChild(element);
    }

    @Override
    public <M> HasValue<M> getHasValue() {
        return null;
    }

    @Override
    public void setSuccess(String validText) {
        if (validText != null) {
            this.helperTextElement.innerHTML = validText;
            this.helperTextElement.classList.add("valid-feedback");
        } else
            this.helperTextElement.classList.remove("valid-feedback");
    }
}
