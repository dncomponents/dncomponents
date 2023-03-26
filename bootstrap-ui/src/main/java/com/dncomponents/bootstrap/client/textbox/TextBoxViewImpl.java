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

package com.dncomponents.bootstrap.client.textbox;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TextBoxViewImpl implements TextBoxView {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLInputElement root;

    HtmlBinder uiBinder = HtmlBinder.get(TextBoxViewImpl.class, this);

    public TextBoxViewImpl() {
        uiBinder.setTemplateContent("<input ui-field=\"root\" class=\"form-control\" type=\"text\">\n");
        uiBinder.bind();
    }


    public TextBoxViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TextBoxViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public String getValue() {
        return asElement().value;
    }

    @Override
    public void setValue(String value) {
        asElement().value = value;
    }

    @Override
    public void addOnInputChangeHandler(EventListener listener) {

    }

    @Override
    public void addOnBlurHandler(OnBlurHandler handler) {
        asElement().addEventListener(handler.getType(), handler);
    }

    @Override
    public void addOnKeyUpHandler(KeyUpHandler handler) {
        asElement().addEventListener(handler.getType(), handler);
    }

    @Override
    public void setError(boolean b) {
        if (b)
            root.classList.add("is-invalid");
        else
            root.classList.remove("is-invalid");
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        setError(errorMessage != null);
    }

    @Override
    public void setValid(boolean b) {
        if (b)
            root.classList.add("is-valid");
        else
            root.classList.remove("is-valid");
    }

    @Override
    public void setPlaceHolder(String placeHolder) {
        root.setAttribute("placeholder", placeHolder);
    }

    @Override
    public void setLabel(String label) {

    }

    @Override
    public HTMLInputElement asElement() {
        return root;
    }

}
