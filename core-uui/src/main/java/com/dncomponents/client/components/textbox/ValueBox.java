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

package com.dncomponents.client.components.textbox;

import com.dncomponents.client.components.core.BaseFocusComponent;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.validator.CanShowError;
import com.dncomponents.client.components.core.events.validator.CanShowSuccess;
import com.dncomponents.client.components.core.events.validator.ValidationEvent;
import com.dncomponents.client.components.core.events.validator.ValidationHandler;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.components.core.validation.HasValidation;
import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.components.core.validation.Validator;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import elemental2.dom.DomGlobal;
import elemental2.dom.FocusEvent;


public abstract class ValueBox<T> extends BaseFocusComponent<Object, TextBoxView> implements
        HasValue<T>, HasValueParser<T>, CanShowError, HasValidation<T>, CanShowSuccess {

    T value;
    private boolean triggerOnBlur = true;
    private Validator<T> validator;
    private boolean valid = true;
    private String errorMsg;

    public ValueBox(TextBoxView view) {
        super(view);
        bind();
    }

    abstract T parseString(String str) throws ValidationException;

    abstract String render(T t);

    private void bind() {
        view.addOnBlurHandler(new OnBlurHandler() {
            @Override
            public void onBlur(FocusEvent focusEvent) {
                if (!triggerOnBlur) return;
                setParsedValue();
            }
        });
        view.addOnKeyUpHandler(event -> {
            if (event.key.equals("Enter"))
                setParsedValue();
        });
    }

    private void setParsedValue() {
        T parsedValue;
        try {
            parsedValue = parseString(view.getValue());
            setValue(parsedValue, true);
        } catch (ValidationException ex) {
            handleValidationException(ex);
            DomGlobal.setTimeout(e -> {
                view.setValue(render(value));
                view.setError(false);
            }, 300);
        }
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        setValue(value, false);
    }

    @Override
    public void setValue(T value, boolean fireEvents) {
        try {
            validate(value);
            T oldValue = getValue();
            this.value = value;
            view.setValue(render(value));
            if (fireEvents) {
                T newValue = getValue();
                ValueChangeEvent.fireIfNotEqual(this, oldValue, newValue);
            }
        } catch (ValidationException e) {
            handleValidationException(e);
        }
    }

    private void handleValidationException(ValidationException e) {
        valid = false;
        ValidationEvent.fire(ValueBox.this, value, e.getMessage());
        view.setError(true);
        view.setErrorMessage(e.getMessage());
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
        return handler.addTo(asElement());
    }

    /**
     * Sets whether value is set from parsed string of text field on blur event
     * default is <code>true<code/>
     *
     * @param b <code>true<code/> if triggers parsing text on blur event
     */
    public void setTriggerOnBlur(boolean b) {
        this.triggerOnBlur = b;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getValueOrThrow() throws ValidationException {
        String text = view.getValue();
        T parseResult = parseString(text);
        if ("".equals(text)) {
            return null;
        }
        return parseResult;
    }

    public void setLabel(String label) {
        view.setLabel(label);
    }

    public void setHelperText(String helperText) {
        view.setHelperText(helperText);
    }

    /**
     * @param error message to show
     *              null to remove error message and error styling
     */
    @Override
    public void showErrorMessage(String error) {
        this.errorMsg = error;
        view.setErrorMessage(error);
    }

    @Override
    public void showSuccessMessage(String valid) {
        view.setValid(valid != null);
    }

    @Override
    public void setErrorStyle(boolean b) {
        view.setError(b);
    }

    @Override
    public void setSuccessStyle(boolean b) {
        view.setValid(b);
    }

    public void setPlaceHolder(String placeHolder) {
        view.setPlaceHolder(placeHolder);
    }

    @Override
    public HandlerRegistration addValidationHandler(ValidationHandler<T> handler) {
        return addHandler(handler);
    }

    protected static final String VALUE = "val";

    @Override
    public boolean isValidEntry() {
        return valid;
    }

    @Override
    public void setValidator(Validator<T> validator) {
        this.validator = validator;
    }

    @Override
    public void validate(T value) throws ValidationException {
        if (validator != null) {
            validator.validate(value);
            this.valid = true;
            if (errorMsg == null)
                view.setError(false);
        }
    }
}
