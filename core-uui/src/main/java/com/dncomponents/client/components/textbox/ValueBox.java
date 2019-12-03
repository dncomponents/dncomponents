package com.dncomponents.client.components.textbox;

import com.dncomponents.client.components.core.BaseFocusComponent;
import com.dncomponents.client.components.core.events.validator.ValidationEvent;
import com.dncomponents.client.components.core.events.validator.ValidationHandler;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.components.core.events.validator.HasValidationHandlers;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import elemental2.dom.FocusEvent;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nikolasavic
 */
public abstract class ValueBox<T> extends BaseFocusComponent<Object, TextBoxView> implements
        HasValue<T>, HasValueParser<T>, HasValidationHandlers<T> {

    T value;
    private boolean triggerOnBlur = true;

    private List<Validator<T>> validators = new ArrayList<>();
    public void addValidator(Validator<T> validator) {
        validators.add(validator);
    }


    public List<String> validate(T value) {
        List<String> errors = new ArrayList<>();
        for (Validator<T> validator : validators) {
            String errMsg = validator.validate(value);
            if (errMsg != null)
                errors.add(errMsg);
        }
        return errors;
    }

    public ValueBox(TextBoxView view) {
        super(view);
        bind();
    }

    abstract T parseString(String str);

    abstract String render(T t);

    private void bind() {
        view.addOnBlurHandler(new OnBlurHandler() {
            @Override
            public void onBlur(FocusEvent focusEvent) {
                if (!triggerOnBlur) return;
                T parsedValue = parseString(view.getValue());
                setValue(parsedValue, true);
            }
        });
        view.addOnKeyUpHandler(event -> {
            if (event.key.equals("Enter"))
                setValue(parseString(view.getValue()), true);
        });
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
        List<String> validationErrors = validate(value);
        if (!validationErrors.isEmpty()) {
            ValidationEvent.fire(ValueBox.this, value, validationErrors);
            view.setError(true);
            return;
        }
        view.setError(false);
        T oldValue = getValue();
        this.value = value;
        view.setValue(render(value));
        if (fireEvents) {
            T newValue = getValue();
            ValueChangeEvent.fireIfNotEqual(this, oldValue, newValue);
        }
//        ValidationEvent.fire(this, value, validationErrors);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
        return ensureHandlers().addHandler(ValueChangeEvent.getType(), handler);
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
    public T getValueOrThrow() throws ParseException {
        String text = view.getValue();

        T parseResult = parseString(text);

        if ("".equals(text)) {
            return null;
        }

        return parseResult;
    }

    @Override
    public HandlerRegistration addValidationHandler(ValidationHandler<T> handler) {
        return addHandler(handler);
    }

    protected static final String VALUE = "val";

}
