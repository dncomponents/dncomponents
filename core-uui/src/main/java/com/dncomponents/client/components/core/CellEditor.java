package com.dncomponents.client.components.core;

import com.dncomponents.client.components.core.events.focus.HasBlurHandlers;
import com.dncomponents.client.views.IsElement;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasValue;
/**
 * @author nikolasavic
 */
public interface CellEditor<M> {
    HasValue<M> getHasValue();

    Focusable getFocusable();

    IsElement getIsElement();

    void startEditing();

    HasBlurHandlers getHasBlurHandler();

    void setEndEditingHandler(Command endEditing);
}
