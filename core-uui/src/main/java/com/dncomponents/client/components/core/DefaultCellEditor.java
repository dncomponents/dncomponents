package com.dncomponents.client.components.core;

import com.dncomponents.client.components.HasSelectionModel;
import com.dncomponents.client.components.checkbox.HasIsElement;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.focus.Focusable;
import com.dncomponents.client.components.core.events.focus.HasBlurHandlers;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.selectionmodel.HasEntityMultiSelectionModel;
import com.dncomponents.client.components.core.selectionmodel.HasEntitySingleSelectionModel;
import com.dncomponents.client.views.IsElement;

/**
 * @author nikolasavic
 */
public class DefaultCellEditor<M> implements CellEditor<M> {

    protected Object c;

    protected Command endEditing;

    public DefaultCellEditor(Object c) {
        this.c = c;
    }

    @Override
    public HasValue<M> getHasValue() {
        if (c instanceof HasEntitySingleSelectionModel)
            return ((HasEntitySingleSelectionModel) c).getEntitySelectionModel().getHasValue();
        else if (c instanceof HasEntityMultiSelectionModel)
            return ((HasEntityMultiSelectionModel) c).getEntitySelectionModel().getHasValue();
        else if (c instanceof HasSelectionModel)
            return ((HasSelectionModel) c).getSelectionModel().getHasValue();
        else
            return (HasValue<M>) c;
    }

    @Override
    public Focusable getFocusable() {
        return (Focusable) c;
    }

    @Override
    public IsElement getIsElement() {
        if (c instanceof HasIsElement)
            return ((HasIsElement) c).asIsElement();
        else
            return (IsElement) c;
    }

    @Override
    public void startEditing() {

    }

    @Override
    public HasBlurHandlers getHasBlurHandler() {
        return (HasBlurHandlers) c;
    }

    @Override
    public void setEndEditingHandler(Command endEditing) {
        this.endEditing = endEditing;
    }

}
