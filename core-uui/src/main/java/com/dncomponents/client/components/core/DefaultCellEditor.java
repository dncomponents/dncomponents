package com.dncomponents.client.components.core;

import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.focus.Focusable;
import com.dncomponents.client.components.core.events.focus.HasBlurHandlers;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.views.IsElement;
/**
 * @author nikolasavic
 */
public class DefaultCellEditor<M, C extends HasValue & Focusable & IsElement & HasBlurHandlers> implements CellEditor<M> {

    C c;

    protected Command endEditing;

    public DefaultCellEditor(C c) {
        this.c = c;
    }

    @Override
    public HasValue<M> getHasValue() {
        return c;
    }

    @Override
    public Focusable getFocusable() {
        return c;
    }

    @Override
    public IsElement getIsElement() {
        return c;
    }

    @Override
    public void startEditing() {

    }

    @Override
    public HasBlurHandlers getHasBlurHandler() {
        return c;
    }

    @Override
    public void setEndEditingHandler(Command endEditing) {
        this.endEditing = endEditing;
    }

}
