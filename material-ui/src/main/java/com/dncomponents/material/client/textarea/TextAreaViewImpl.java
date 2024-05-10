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

package com.dncomponents.material.client.textarea;

import com.dncomponents.Template;
import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.material.client.textbox.TextBoxType;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.HTMLTextAreaElement;


@Template
public class TextAreaViewImpl extends BaseTextView {

    @UiField
    String focusTextAreaStyle;
    @UiField
    String focusFloatingLabelStyle;
    //
    @UiField
    protected HTMLTextAreaElement inputElement;
    HtmlBinder uiBinder = HtmlBinder.create(TextAreaViewImpl.class, this);

    public TextAreaViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        currentRoot = wrapperRoot;
        init();
    }

    private void setBorderFocused(boolean b) {
        if (b) {
            root.classList.add(focusTextAreaStyle);
        } else {
            root.classList.remove(focusTextAreaStyle);
        }
    }

    @Override
    protected HTMLElement getInputElement() {
        return inputElement;
    }

    @Override
    protected String getInputElementValue() {
        return inputElement.value;
    }

    @Override
    protected void setInputElementValue(String value) {
        inputElement.value = value;
    }

    public static class TextAreaViewBuilder extends BaseTextViewBuilder<TextAreaViewBuilder> {

        public static TextAreaViewBuilder get() {
            return new TextAreaViewBuilder();
        }

        public TextAreaViewBuilder() {
        }

        @Override
        protected void init(BaseTextView v) {
            if (type == null)
                type = TextBoxType.OUTLINED;
            super.init(v);
        }

        public <B extends BaseTextView> B build() {
            if (templateElement == null)
                templateElement = MaterialUi.getUi().textarea;
            final TextAreaViewImpl view = new TextAreaViewImpl(templateElement);
            init(view);
            return (B) view;
        }
    }

}
