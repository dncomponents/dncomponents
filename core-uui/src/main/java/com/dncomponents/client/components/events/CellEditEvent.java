package com.dncomponents.client.components.events;

import com.dncomponents.client.components.CellServerResponse;
import com.dncomponents.client.components.BaseCell;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import elemental2.dom.MouseEvent;

/**
 * Created by nikolasavic
 */

public class CellEditEvent<T> extends AbstractCellEvent<T, CellEditEvent.CellEditHandler> {


    CellServerResponse cellServerResponse;
    private static Type<CellEditHandler> TYPE;

    public CellEditEvent(BaseCell<T, ?> cell) {
        super(cell);
    }

    public CellEditEvent(BaseCell<T, ?> cell, MouseEvent mouseEvent) {
        super(cell, mouseEvent);
    }

    public CellEditEvent(BaseCell<T, ?> cell, CellServerResponse cellServerResponse) {
        super(cell, null);
        this.cellServerResponse = cellServerResponse;
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<CellEditHandler> getType() {
        if (TYPE == null) {
            TYPE = new Type<>();
        }
        return TYPE;
    }

    @Override
    public Type<CellEditHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CellEditHandler handler) {
        handler.onCellEvent(this);
    }


    public interface CellEditHandler<T> extends EventHandler {
        void onCellEvent(CellEditEvent<T> event);
    }

    public interface HasCellEditHandler<T> extends HasHandlers {
        HandlerRegistration addCellEditHandler(CellEditHandler<T> handler);
    }

    public CellServerResponse getCellServerResponse() {
        return cellServerResponse;
    }
}
