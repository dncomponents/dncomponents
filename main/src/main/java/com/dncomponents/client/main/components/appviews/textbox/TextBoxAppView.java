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

package com.dncomponents.client.main.components.appviews.textbox;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.validator.ValidationEvent;
import com.dncomponents.client.components.core.events.validator.ValidationHandler;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.textbox.*;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.Date;


public class TextBoxAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    public TextBox textBox;
    @UiField
    public TextArea ta;
    @UiField
    public IntegerBox integerBox;
    @UiField
    public LongBox longBox;
    @UiField
    public DoubleBox doubleBox;
    @UiField
    public DateBox dateBox;
    @UiField
    public TextArea textArea;

    public TextBoxAppView() {
        HtmlBinder.create(TextBoxAppView.class, this).bind();
        init();
        initEvents();
    }

    private void init() {
        TextBox textBox = new TextBox();
        textBox.setValue("text box value");
        IntegerBox integerBox = new IntegerBox();
        integerBox.setValue(333);
        LongBox longBox = new LongBox();
        longBox.setValue(333333333333333L);
        DoubleBox doubleBox = new DoubleBox();
        doubleBox.setValue(33.3);
        DateBox dateBox = new DateBox();
        dateBox.setValue(new Date());
        TextArea textArea = new TextArea();
        textArea.setValue("text area value");
    }

    private void initEvents() {
        ValueChangeHandler changeHandler = event -> {
            ta.setValue(event.getValue() + "");
        };
        textBox.addValueChangeHandler(changeHandler);
        integerBox.addValueChangeHandler(changeHandler);
        longBox.addValueChangeHandler(changeHandler);
        doubleBox.addValueChangeHandler(changeHandler);
        dateBox.addValueChangeHandler(changeHandler);
        textArea.addValueChangeHandler(changeHandler);
//        textBox.addValidator(new Validator<String>() {
//            @Override
//            public String validate(String value) {
//                if (value.equals("bla")) {
//                    return "this can't be bla";
//                } else
//                    return null;
//            }
//        });
        textBox.addValidationHandler(new ValidationHandler<String>() {
            @Override
            public void onValidation(ValidationEvent<String> event) {
                ta.setValue(event.getFirstError());
            }
        });

    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static TextBoxAppView instance;

    public static TextBoxAppView getInstance() {
        if (instance == null) instance = new TextBoxAppView();
        return instance;
    }
}
