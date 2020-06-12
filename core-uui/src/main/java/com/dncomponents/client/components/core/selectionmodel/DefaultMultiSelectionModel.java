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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolasavic
 */
public abstract class DefaultMultiSelectionModel<M> implements MultiSelectionModel<M> {
    protected SelectionMode selectionMode = SelectionMode.MULTI;
    protected List<M> selection = new ArrayList<>();
    private HTMLElement bus;

    public List<M> getSelection() {
        return new ArrayList<>(selection);
    }

    public M getFirstSelected() {
        return !selection.isEmpty() ? selection.get(0) : null;
    }

    public boolean isSelected(M item) {
        return selection.contains(item);
    }

    public void setSelected(List<M> models, boolean b, boolean fireEvent) {
        boolean changed = false;
        for (M model : models) {
            boolean b1 = setSelected(model, b, fireEvent);
            if (b1 && !changed) {
                changed = true;
            }
        }
        if (changed && fireEvent)
            fireSelectionChange();
    }

    @Override
    public boolean setSelected(M model, boolean b) {
        return setSelected(model, b, false);
    }

    public boolean setSelected(M model, boolean b, boolean fireEvent) {
        boolean changed = false;
        if (!getItems().contains(model))
            return false;
        if (b) {
            if (!isSelected(model)) {
                if (selectionMode == SelectionMode.SINGLE) {
                    selectAll(false, false);
                }
                selection.add(model);
                changed = true;
            }
        } else {
            if (isSelected(model)) {
                selection.remove(model);
                changed = true;
            }
        }
        if (changed)
            setSelectedInView(model, b);

        if (changed && fireEvent) {
            fireSelectionChange();
        }
        return changed;
    }

    public void setSelectedInView(M model, boolean b) {

    }

    public abstract List<M> getItems();

    public void selectAll(Boolean value, boolean fire) {
        if (value) {
            selection.clear();
            selection.addAll(getItems());
        } else {
            selection.clear();
        }
        getItems().forEach(m -> setSelectedInView(m, value));
        if (fire)
            fireSelectionChange();
    }

    public void clearSelection(boolean fire) {
        selectAll(false, fire);
    }

    public SelectionMode getSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(SelectionMode selectionMode) {
        this.selectionMode = selectionMode;
    }

    public boolean isAllSelected() {
        return selection.size() == getItems().size() && getItems().size() > 0;
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<M>> handler) {
        return handler.addTo(ensureHandlers());
    }

    public enum SelectionMode {
        SINGLE, MULTI
    }

    protected HTMLElement ensureHandlers() {
        if (bus == null)
            bus = DomUtil.createDiv();
        return bus;
    }

    @Override
    public void fireEvent(CustomEvent event) {
        ensureHandlers().dispatchEvent(event);
    }

    protected void fireSelectionChange() {
        SelectionEvent.fire(this::ensureHandlers, selection);
        ValueChangeEvent.fire(getHasValue(), selection);
    }

    class HasValueModel extends AbstractValueChangeHandler<List<M>> {

        @Override
        public List<M> getValue() {
            return getSelection();
        }

        @Override
        public void setValue(List<M> value, boolean fireEvents) {
            List<M> oldValue = getValue();
            if (value == null) {
                selectAll(false, false);
                if (fireEvents)
                    ValueChangeEvent.fireIfNotEqual(this, oldValue, getValue());
            } else if (!(oldValue.containsAll(value) && oldValue.size() == value.size())) {
                selectAll(false, false);
                setSelected(value, true, true);
                if (fireEvents)
                    ValueChangeEvent.fireIfNotEqual(this, oldValue, getValue());
            }
        }
    }

    private HasValue<List<M>> hasValue = new HasValueModel();

    @Override
    public HasValue<List<M>> getHasValue() {
        return hasValue;
    }
}