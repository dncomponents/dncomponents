package com.dncomponents.client.components.core.selection;

/**
 * Created by nikolasavic
 */

import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.google.gwt.event.shared.GwtEvent;

import java.util.Collections;
import java.util.List;

/**
 * Fires after the selection changes.
 */
public class SelectionChangedEvent<M> extends GwtEvent<SelectionChangedHandler<M>> {

    /**
     * Handler type.
     */
    private static Type<SelectionChangedHandler<?>> TYPE;
    private List<M> selection;

    public SelectionChangedEvent(List<M> selection) {
        this.selection = Collections.unmodifiableList(selection);
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<SelectionChangedHandler<?>> getType() {
        if (TYPE == null) {
            TYPE = new Type<>();
        }
        return TYPE;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Type<SelectionChangedHandler<M>> getAssociatedType() {
        return (Type) TYPE;
    }

    /**
     * Returns the selection.
     *
     * @return the selection
     */
    public List<M> getSelection() {
        return selection;
    }

    public M getFirstResult() {
        if (selection.isEmpty())
            return null;
        return selection.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public DefaultMultiSelectionModel<M> getSource() {
        return (DefaultMultiSelectionModel<M>) super.getSource();
    }

    @Override
    protected void dispatch(SelectionChangedHandler<M> handler) {
        handler.onSelectionChanged(this);
    }

}