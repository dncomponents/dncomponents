package com.dncomponents.client.components.core.selection;

import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Created by nikolasavic
 */
public class SelectionHighlightEvent<M> extends GwtEvent<SelectionHighlightEvent.SelectionHighlightChangedHandler<M>> {

    /**
     * Handler type.
     */
    private static Type<SelectionHighlightChangedHandler<?>> TYPE;
    private M selection;

    public SelectionHighlightEvent(M selection) {
        this.selection = selection;
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<SelectionHighlightChangedHandler<?>> getType() {
        if (TYPE == null) {
            TYPE = new Type<>();
        }
        return TYPE;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Type<SelectionHighlightChangedHandler<M>> getAssociatedType() {
        return (Type) TYPE;
    }

    /**
     * Returns the selection.
     *
     * @return the selection
     */
    public M getSelection() {
        return selection;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DefaultMultiSelectionModel<M> getSource() {
        return (DefaultMultiSelectionModel<M>) super.getSource();
    }

    @Override
    protected void dispatch(SelectionHighlightChangedHandler<M> handler) {
        handler.onSelectionChanged(this);
    }

    /**
     * A widget that implements this interface is a public source of
     * {@link SelectionChangedEvent} events.
     *
     * @param <M> the model type
     */
    public interface HasSelectionHighlightChangedHandlers<M> {

        /**
         * Adds a {@link SelectionHighlightChangedHandler} handler for
         * {@link SelectionChangedEvent} events.
         *
         * @param handler the handler
         * @return the registration for the event
         */
        HandlerRegistration addSelectionHighlightChangedHandler(SelectionHighlightChangedHandler<M> handler);
    }

    /**
     * Handler class for {@link SelectionChangedEvent} events.
     */
    public interface SelectionHighlightChangedHandler<M> extends EventHandler {

        /**
         * Called after a widget's selections are changed.
         */
        void onSelectionChanged(SelectionHighlightEvent<M> event);
    }

}

