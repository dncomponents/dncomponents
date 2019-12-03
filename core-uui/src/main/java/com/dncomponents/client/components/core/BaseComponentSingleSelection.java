package com.dncomponents.client.components.core;

import com.dncomponents.client.components.core.selectionmodel.SingleSelectionModel;
import com.dncomponents.client.components.core.selectionmodel.AbstractSingleSelectionGroup;
import com.dncomponents.client.views.core.pcg.View;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;

import java.util.List;

public class BaseComponentSingleSelection<T, V extends View, C extends HasUserValue<T> & CanSelect>
        extends BaseComponent<T, V> implements SingleSelectionModel<C> {

    protected AbstractSingleSelectionGroup<T, C> selectionGroup = new AbstractSingleSelectionGroup<T, C>() {
        @Override
        public void setSelectedInView(C model, boolean b) {
            model.setSelected(b);
        }
    };

    public BaseComponentSingleSelection(V ui) {
        super(ui);
    }

    @Override
    public boolean setSelected(C c, boolean b, boolean fireEvent) {
        return selectionGroup.setSelected(c, b, fireEvent);
    }

    @Override
    public boolean setSelected(C c, boolean b) {
        return setSelected(c, b, false);
    }

    @Override
    public boolean isSelected(C c) {
        return selectionGroup.isSelected(c);
    }

    @Override
    public List<C> getItems() {
        return selectionGroup.getItems();
    }

    @Override
    public C getSelection() {
        return selectionGroup.getSelection();
    }


    public void addItem(C item) {
        selectionGroup.addItem(item);
    }

    public void removeItem(C item) {
        selectionGroup.removeItem(item);
    }

    public void addItems(C... items) {
        selectionGroup.addItems(items);
    }

    public SingleSelectionModel<T> getEntitySelectionModel() {
        return selectionGroup.getEntitySelectionModel();
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<C> handler) {
        return selectionGroup.addSelectionHandler(handler);
    }
}
