package com.dncomponents.client.views.core.ui.checkbox;


import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.handlers.OnChangeHandler;
import com.dncomponents.client.views.FocusComponentView;
import com.dncomponents.client.views.MainViewSlots;

/**
 * @author nikolasavic
 */
public interface CheckBoxView extends FocusComponentView, MainViewSlots.HasMainViewSlots {
    boolean isChecked();

    void setChecked(Boolean b);

    void setIndeterminate(boolean b);

    void setLabel(String txt);

    HandlerRegistration addOnChangeHandler(OnChangeHandler changeHandler);

    void setName(String nameOfGroup);

    String getLabel();
}
