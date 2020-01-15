package com.dncomponents.client.components.events;

import com.dncomponents.client.components.core.CellConfig;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Created by nikolasavic
 */
public class SortEvent extends GwtEvent<SortEvent.SortHandler> {

    /**
     * Handler type.
     */
    private static Type<SortHandler> TYPE;
    private CellConfig columnConfig;
    private EventType eventType = EventType.NONE;

    public SortEvent() {
    }

    public SortEvent(EventType eventType) {
        this.eventType = eventType;
    }

    public SortEvent(EventType eventType, CellConfig columnTableConfig) {
        this.eventType = eventType;
        this.columnConfig = columnTableConfig;
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<SortHandler> getType() {
        if (TYPE == null) {
            TYPE = new Type<>();
        }
        return TYPE;
    }

    public CellConfig getColumnConfig() {
        return columnConfig;
    }

    public void setColumnConfig(CellConfig columnConfig) {
        this.columnConfig = columnConfig;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public Type<SortHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SortHandler handler) {
        handler.onSortEvent(this);
    }

    public enum EventType {
        SORT_ASCD,
        SORT_DESC,
        GROUP_BY,
        NONE
    }

    public interface SortHandler extends EventHandler {
        void onSortEvent(SortEvent event);
    }


    public interface HasSortHandler {
        HandlerRegistration addSortHandler(SortHandler handler);
    }

}
