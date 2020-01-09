package com.dncomponents.client.components.checkbox;

import com.dncomponents.client.components.core.BaseFocusComponent;
import com.dncomponents.client.components.modal.SetElement;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxViewSlots;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public abstract class AbstractCheckBox<T> extends BaseFocusComponent<T, CheckBoxView> implements HasValue<Boolean> {
    Boolean value;

    protected boolean fromView;

    public AbstractCheckBox(CheckBoxView view) {
        super(view);
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
        return ensureHandlers().addHandler(ValueChangeEvent.getType(), handler);
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
    public CheckBoxViewSlots getViewSlots() {
        return (CheckBoxViewSlots) super.getViewSlots();
    }

}