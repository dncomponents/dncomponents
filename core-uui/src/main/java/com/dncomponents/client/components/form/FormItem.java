package com.dncomponents.client.components.form;

import com.dncomponents.client.components.core.FieldConfig;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.validator.CanShowError;
import com.dncomponents.client.components.core.events.validator.CanShowSuccess;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.events.value.HasValueChangeHandlers;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.components.core.validation.*;
import com.dncomponents.client.components.multi.BaseHasView;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.form.FormItemView;
import elemental2.dom.CustomEvent;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public class FormItem<T, M> extends BaseHasView<M, FormItemView> implements HasModel<T>, HasValueChangeHandlers<M> {

    private FieldConfig<T, M> fieldConfig;
    private T model;
    private HasValue<M> hasValue;
    Command setValueCmd;
    String hasValueUiField;

    public FormItem() {
        this(Ui.get().getFormUi().getFormItemView());
        bind();
    }

    public FormItem(FormItemView view) {
        super(view);
    }

    public FormItem(Form form) {
        super(form.getView().getFormItemView());
        this.hasValue = view.getHasValue();
        bind();
    }

    public void setHasValue(HasValue<M> hasValue) {
        this.hasValue = hasValue;
        if (hasValue instanceof IsElement)
            view.setContent(((IsElement) hasValue).asElement());
        initValueHandler();
    }

    public void setIsElement(IsElement element) {
        view.setContent(element.asElement());
    }

    public void setFieldConfig(FieldConfig<T, M> fieldConfig) {
        this.fieldConfig = fieldConfig;
        view.setLabelText(fieldConfig.getName());
        view.setHelperText(fieldConfig.getHelperText());
    }

    public boolean isEditable() {
        return fieldConfig.getFieldSetter() != null;
    }

    private void bind() {
        initValueHandler();
    }

    private void initValueHandler() {
        if (hasValue != null)
            hasValue.addValueChangeHandler(event -> {
                setValue(event.getValue(), true);
            });
//        if (hasValue instanceof HasBlurHandlers && !firstCall && !(hasValue instanceof Autocomplete))
//            ((HasBlurHandlers) hasValue).addBlurHandler(focusEvent ->
//                    setValue(hasValue.getValue(), true));
    }

    private void setValue(M value, boolean fireEvents) {
        if (!validate(value)) {
            return;
        }
        M oldValue = getValue();
        hasValue.setValue(value, fireEvents);
        setValueCmd = this::setValueToModel;
        if (!fireEvents) {
            view.setHelperText(getHelperText());
        }
        if (fireEvents) {
            M newValue = getValue();
            ValueChangeEvent.fireIfNotEqual(this, oldValue, newValue);
        }
    }

    private String getHelperText() {
        return fieldConfig != null && fieldConfig.getHelperText() != null ? fieldConfig.getHelperText() : null;
    }

    private String getSuccessText() {
        return fieldConfig != null && fieldConfig.getSuccessText() != null ? fieldConfig.getSuccessText() : null;
    }

    private Validator getValidator() {
        return fieldConfig != null && fieldConfig.getValidator() != null ? fieldConfig.getValidator() : null;
    }

    private boolean validate(M value) {
        if (fieldConfig.getFieldSetter() == null)
            return true;
        try {
            if (getValidator() != null) {
                setModelToValidator(getValidator(), getModel());
                Validator val=getValidator();
                getValidator().validate(value);
            }
            setValueCmd = this::setValueToModel;
            setErrorOnView(null);
            setSuccessOnView(getSuccessText());
        } catch (ValidationException e) {
            setValueCmd = null;
            setErrorOnView(e.getMessage());
            return false;
        }

        return true;
    }

    private static void setModelToValidator(Validator validator, Object model) {
        if (validator != null) {
            if (validator instanceof Validators) {
                for (Validator val : ((Validators<?>) validator).getValidators()) {
                    if (val instanceof ModelValidator)
                        ((ModelValidator) val).setModel(model);
                }
            } else if (validator instanceof ModelValidator)
                ((ModelValidator) validator).setModel(model);
        }
    }

    private void setSuccessOnView(String success) {
        if (hasValue instanceof CanShowSuccess) {
            ((CanShowSuccess) hasValue).showSuccessMessage(success);
            ((CanShowSuccess) hasValue).setSuccessStyle(true);
        } else {
            view.setSuccess(success);
            view.setSuccessStyle(true);
        }
    }

    private void setErrorOnView(String error) {
        view.setError(error);
        if (hasValue instanceof CanShowError) {
            ((CanShowError) hasValue).showErrorMessage(error);
        } else {
            view.setError(error);
            view.setErrorStyle(error != null);
        }
        if (hasValue instanceof CanShowSuccess)
            ((CanShowSuccess) hasValue).showSuccessMessage(error == null ? "" : null);
    }

    boolean isValid() {
        return validate(hasValue.getValue());
    }

    @Override
    public void setValueToModel() {
        fieldConfig.getFieldSetter().accept(model, hasValue.getValue());
    }

    @Override
    public void setValueFromModel() {
        if (fieldConfig.getFieldSetter() == null) {
            if (fieldConfig.getRenderer() != null && model != null && fieldConfig.getFieldGetter().apply(model) != null)
                fieldConfig.getRenderer().setValue(fieldConfig.getFieldGetter().apply(model), view.getMainPanel());
        } else if (model != null && fieldConfig.getFieldGetter().apply(model) != null) {
            hasValue.setValue(fieldConfig.getFieldGetter().apply(model));
        }
    }

    @Override
    public T getModel() {
        return this.model;
    }

    @Override
    public void setModel(T model) {
        this.model = model;
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<M> handler) {
        return handler.addTo(asElement());
    }

    @Override
    public void fireEvent(CustomEvent event) {
        asElement().dispatchEvent(event);
    }

    public void setLabel(String label) {
        view.setLabelText(label);
    }

    public void setHelperText(String helperText) {
        view.setHelperText(helperText);
    }

    public void setSuccessText(String successText) {
        view.setSuccess(successText);
    }

    HTMLElement getMainPanelContent() {
        return (HTMLElement) view.getMainPanel().firstElementChild;
    }
}
