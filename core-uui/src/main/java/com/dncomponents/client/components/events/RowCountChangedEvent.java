package com.dncomponents.client.components.events;

import com.dncomponents.client.components.HasRows;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Created by nikolasavic
 */
public class RowCountChangedEvent extends GwtEvent<RowCountChangedEvent.Handler> {

    /**
     * Handler type.
     */
    private static Type<Handler> TYPE;
    private final int rowCount;
    private final boolean isExact;

    /**
     * Creates a {@link RowCountChangedEvent}.
     *
     * @param rowCount the new row count
     * @param isExact  true if the row count is exact
     */
    protected RowCountChangedEvent(int rowCount, boolean isExact) {
        this.rowCount = rowCount;
        this.isExact = isExact;
    }

    /**
     * Fires a {@link RowCountChangedEvent} on all registered handlers in the
     * handler manager. If no such handlers exist, this method will do nothing.
     *
     * @param isExact  true if rowCount is an exact count
     * @param source   the source of the handlers
     * @param rowCount the new rowCount
     */
    public static void fire(HasRows source, int rowCount, boolean isExact) { //TODO remove isExact
        if (TYPE != null) {
            RowCountChangedEvent event = new RowCountChangedEvent(rowCount, isExact);
            source.fireEvent(event);
        }
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<Handler> getType() {
        if (TYPE == null) {
            TYPE = new Type<>();
        }
        return TYPE;
    }

    @Override
    public final Type<Handler> getAssociatedType() {
        return TYPE;
    }

    /**
     * Gets the new row count.
     *
     * @return the new row count
     */
    public int getNewRowCount() {
        return rowCount;
    }

    /**
     * Check if the new row count is exact.
     *
     * @return true if the new row count is exact, false if not
     */
    public boolean isNewRowCountExact() {
        return isExact;
    }

    @Override
    public String toDebugString() {
        return super.toDebugString() + getNewRowCount();
    }

    @Override
    protected void dispatch(Handler handler) {
        handler.onRowCountChange(this);
    }

    /**
     * Handler interface for {@link RowCountChangedEvent} events.
     */
    public interface Handler extends EventHandler {

        /**
         * Called when a {@link RowCountChangedEvent} is fired.
         *
         * @param event the {@link RowCountChangedEvent} that was fired
         */
        void onRowCountChange(RowCountChangedEvent event);
    }

    public interface HasRowCountHandler extends HasHandlers {
        HandlerRegistration addRowCountChangedHandler(Handler handler);
    }

}
