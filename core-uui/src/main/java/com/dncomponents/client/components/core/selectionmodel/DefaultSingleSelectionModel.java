package com.dncomponents.client.components.core.selectionmodel;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.selectionmodel.helper.AbstractValueChangeHandler;
import com.dncomponents.client.dom.DomUtil;
import elemental2.dom.CustomEvent;
import elemental2.dom.HTMLElement;

import java.util.List;

public abstract class DefaultSingleSelectionModel<T> implements SingleSelectionModel<T> {

    T selection;
    T lastSelection;

    private HTMLElement bus;

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
        SelectionEvent.fire(ensureHandlers(), selection);
        ValueChangeEvent.fire(hasValue, selection);
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
        return handler.addTo(ensureHandlers());
    }

    @Override
    public void fireEvent(CustomEvent event) {
        ensureHandlers().dispatchEvent(event);
    }

    private HTMLElement ensureHandlers() {
        if (bus == null)
            bus = DomUtil.createDiv();
        return bus;
    }

    class HasValueModel extends AbstractValueChangeHandler<T> {

        @Override
        public T getValue() {
            return getSelection();
        }

        @Override
        public void setValue(T value, boolean fireEvents) {
            T oldValue = getValue();
            setSelected(value, true, true);
            if (fireEvents)
                ValueChangeEvent.fireIfNotEqual(this, oldValue, getValue());
        }
    }

    private HasValue<T> hasValue = new HasValueModel();

    @Override
    public HasValue<T> getHasValue() {
        return hasValue;
    }
}
