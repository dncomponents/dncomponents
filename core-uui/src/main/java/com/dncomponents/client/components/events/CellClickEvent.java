package com.dncomponents.client.components.events;

import com.dncomponents.client.components.BaseCell;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import elemental2.dom.MouseEvent;

/**
 * Created by nikolasavic
 */

public class CellClickEvent<T> extends AbstractCellEvent<T, CellClickEvent.CellClickHandler> {

    /**
     * Handler type.
     */
    private static GwtEvent.Type<CellClickHandler> TYPE;

    public CellClickEvent(BaseCell<T, ?> cell) {
        super(cell);
    }

    public CellClickEvent(BaseCell<T, ?> cell, MouseEvent mouseEvent) {
        super(cell, mouseEvent);
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static GwtEvent.Type<CellClickHandler> getType() {
        if (TYPE == null) {
            TYPE = new GwtEvent.Type<>();
        }
        return TYPE;
    }

    @Override
    public GwtEvent.Type<CellClickHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CellClickHandler handler) {
        handler.onCellEvent(this);
    }

     public interface CellClickHandler extends EventHandler {
        /**
         * Called when {@link CellClickEvent} is fired.
         *
         * @param event the {@link CellClickEvent} that was fired
         */
        void onCellEvent(CellClickEvent event);
    }

    public interface HasCellClickHandler extends HasHandlers {
        HandlerRegistration addCellClickHandler(CellClickHandler handler);
    }


}
