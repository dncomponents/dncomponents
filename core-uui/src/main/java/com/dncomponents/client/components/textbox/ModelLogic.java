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

import com.dncomponents.client.components.core.FieldConfig;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.validator.ValidationHandler;
import com.dncomponents.client.components.core.validation.CellValidator;
import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.components.core.validation.Validator;
import com.dncomponents.client.components.form.IsForm;
import elemental2.dom.CustomEvent;

public class ModelLogic<T> {

    FieldConfig<Object, T> fieldConfig;
    Object model;
    //    private boolean validEntry = true;
    Command setValueCommand;
    Validator<T> validator;
    CellValidator<Object, T> cellValidator;

    public boolean isValidEntry() {
        return setValueCommand != null;
    }

    public void setSetValueCommand(Command setValueCommand) {
        if (fieldConfig != null)
            this.setValueCommand = setValueCommand;
    }

    IsForm<T> form;

    public ModelLogic(IsForm<T> form) {
        this.form = form;
    }

    public <P> void setFieldConfig(FieldConfig<P, T> config) {
        this.fieldConfig = (FieldConfig<Object, T>) config;
        form.setLabel(fieldConfig.getName());
        form.setHelperText(fieldConfig.getHelperText());
//        form.setValidator(fieldConfig.getValidator());
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

    public void setValueToModel() {
        if (setValueCommand != null)
            setValueCommand.execute();
    }

    public void setValueFromModel() {
        form.setValue(fieldConfig.getFieldGetter().apply(model));
    }

    public HandlerRegistration addValidationHandler(ValidationHandler<T> handler) {
        return handler.addTo(form.asElement());
    }

    public void fireEvent(CustomEvent event) {
        form.fireEvent(event);
    }

    public void setValidator(Validator<T> validator) {
        this.validator = validator;
    }

    public void validate(T value) throws ValidationException {
        if (validator != null)
            validator.validate(value);
        if (cellValidator != null)
            cellValidator.validate(value, getModel());
    }

    public void setSetValueCommand(Object model, T value) {
        if (fieldConfig != null)
            this.setValueCommand = () ->
                    fieldConfig.getFieldSetter().accept(model, value);
    }
}
