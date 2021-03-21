package com.dncomponents.client.components.core;

import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.focus.Focusable;
import com.dncomponents.client.components.core.events.focus.HasBlurHandlers;
import com.dncomponents.client.components.core.events.validator.CanShowError;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.views.IsElement;

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

//    void validate();

    default void showErrorMessage(String msg) {
        if (getHasValue() instanceof CanShowError) {
            ((CanShowError) getHasValue()).showErrorMessage(msg);
        }
    }

    abstract class CellEditorSimple<M> implements CellEditor<M> {


        @Override
        public Focusable getFocusable() {
            return null;
        }

        @Override
        public void startEditing() {

        }

        @Override
        public HasBlurHandlers getHasBlurHandler() {
            return null;
        }

        @Override
        public void setEndEditingHandler(Command endEditing) {

        }
    }
}
