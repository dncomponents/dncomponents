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

package com.dncomponents.client.components.checkbox;

import com.dncomponents.client.components.core.BaseFocusComponent;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.validator.CanShowError;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.components.modal.SetElement;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.MainRendererImpl;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;

public abstract class AbstractCheckBox<T> extends BaseFocusComponent<T, CheckBoxView> implements HasValue<Boolean>, CanShowError {
    Boolean value;

    protected boolean fromView;

    public AbstractCheckBox(CheckBoxView view) {
        super(view);
        setRenderer(new MainRendererImpl<T>());
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    public boolean isValueTrue() {
        return value != null && value;
    }

    @Override
    public void setValue(Boolean value) {
        setValue(value, false);
    }

    @Override
    public void setValue(Boolean newValue, boolean fireEvents) {
        Boolean oldValue = getValue();
        value = newValue;
        if (!fromView)
            view.setChecked(value);
        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
        }
    }

    public void setIndeterminate(boolean b) {
        value = null;
        view.setIndeterminate(b);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
        return handler.addTo(asElement());
    }

    public void setLabel(String txt) {
        view.setLabel(txt);
    }

    public void setLabel(SetElement se) {
        se.setHtml(view.getViewSlots().getMainSlot());
    }

    public String getLabel() {
        return view.getLabel();
    }

    public void setName(String nameOfGroup) {
        view.setName(nameOfGroup);
    }

    @Override
    public void showErrorMessage(String error) {
        //view.showError todo
    }

    @Override
    public void setErrorStyle(boolean b) {

    }

    public void setRenderer(MainRenderer<T> renderer) {
        super.setRendererBase(renderer);
    }

}
