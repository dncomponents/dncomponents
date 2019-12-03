package com.dncomponents.client.components.core.selectionmodel;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

import java.util.List;

public abstract class DefaultSingleSelectionModel<T> implements SingleSelectionModel<T> {

    T selection;
    T lastSelection;

    @Override
    public T getSelection() {
        return selection;
    }

    @Override
    public boolean setSelected(T model, boolean b, boolean fireEvent) {
        boolean changed = false;
        if (!getItems().contains(model))
            return false;
        if (selection != model) {
            changed = true;
            lastSelection = selection;
            selection = model;
        }
        if (changed) {
            if (lastSelection != null)
                setSelectedInView(lastSelection, false);
            setSelectedInView(model, b);
        }
        if (changed && fireEvent)
            fireSelectionChange();
        return changed;
    }

    public void setSelectedInView(T model, boolean b) {
    }

    protected void fireSelectionChange() {
        SelectionEvent.fire(this, selection);
    }

    @Override
    public boolean setSelected(T model, boolean b) {
        return setSelected(model, b, false);
    }

    @Override
    public boolean isSelected(T value) {
        return selection == value;
    }

    @Override
    public abstract List<T> getItems();

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<T> handler) {
        return ensureHandlers().addHandler(SelectionEvent.getType(), handler);
    }

    private HandlerManager handlerManager;

    public void fireEvent(GwtEvent<?> event) {
        if (handlerManager != null) {
            handlerManager.fireEvent(event);
        }
    }

    protected HandlerManager ensureHandlers() {
        if (handlerManager == null) {
            handlerManager = new HandlerManager(this);
        }
        return handlerManager;
    }

}
