package com.dncomponents.client.components.core.selectionmodel;

import com.dncomponents.client.components.core.selectionmodel.helper.AbstractValueChangeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolasavic
 */
public abstract class DefaultMultiSelectionModel<M> implements MultiSelectionModel<M> {
    protected SelectionMode selectionMode = SelectionMode.MULTI;
    protected List<M> selection = new ArrayList<>();
    private HandlerManager handlerManager;


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
        return ensureHandlers().addHandler(SelectionEvent.getType(), handler);
    }

    public enum SelectionMode {
        SINGLE, MULTI
    }


    protected HandlerManager ensureHandlers() {
        if (handlerManager == null) {
            handlerManager = new HandlerManager(this);
        }
        return handlerManager;
    }

    protected void fireSelectionChange() {
        SelectionEvent.fire(this, selection);
        ValueChangeEvent.fire(getHasValue(), selection);
    }


    public void fireEvent(GwtEvent<?> event) {
        if (handlerManager != null) {
            handlerManager.fireEvent(event);
        }
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