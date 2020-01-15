package com.dncomponents.client.components.events;

import com.dncomponents.client.components.BaseCell;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import elemental2.dom.MouseEvent;

/**
 * @author nikolasavic
 */
public class CellDoubleClickEvent<T> extends AbstractCellEvent<T,CellDoubleClickEvent.CellDoubleClickHandler> {

    /**
     * Handler type.
     */
    private static GwtEvent.Type<CellDoubleClickHandler> TYPE;

    public CellDoubleClickEvent(BaseCell<T, ?> cell) {
        super(cell);
    }

    public CellDoubleClickEvent(BaseCell<T, ?> cell, MouseEvent mouseEvent) {
        super(cell, mouseEvent);
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static GwtEvent.Type<CellDoubleClickHandler> getType() {
        if (TYPE == null) {
            TYPE = new GwtEvent.Type<>();
        }
        return TYPE;
    }

    @Override
    public GwtEvent.Type<CellDoubleClickHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CellDoubleClickHandler handler) {
        handler.onCellEvent(this);
    }


    public interface CellDoubleClickHandler extends EventHandler {
        /**
         * Called when {@link CellDoubleClickEvent} is fired.
         *
         * @param event the {@link CellDoubleClickEvent} that was fired
         */
        void onCellEvent(CellDoubleClickEvent event);
    }

    public interface HasCellDoubleClickHandler extends HasHandlers {
        HandlerRegistration addCellDoubleClickHandler(CellDoubleClickHandler handler);
    }
}