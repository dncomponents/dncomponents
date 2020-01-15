package com.dncomponents.client.components.table.header.filterevents;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.table.header.HeaderSorting;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

import java.util.LinkedHashSet;

/**
 * @author nikolasavic
 */
public class SortEvent extends AbstractModifierEvent<SortEvent.SortHandler> {

    /**
     * Handler type.
     */
    private static GwtEvent.Type<SortHandler> TYPE;

    public SortEvent(LinkedHashSet<HeaderSorting> modifiers) {
        super(modifiers);
    }

    public static GwtEvent.Type<SortHandler> getType() {
        if (TYPE == null) {
            TYPE = new GwtEvent.Type<>();
        }
        return TYPE;
    }

    @Override
    public HeaderSorting getByColumn(ColumnConfig columnConfig) {
        return (HeaderSorting) super.getByColumn(columnConfig);
    }

    @Override
    public LinkedHashSet<HeaderSorting> getModifiers() {
        return (LinkedHashSet<HeaderSorting>) super.getModifiers();
    }

    @Override
    public GwtEvent.Type<SortHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(SortHandler handler) {
        handler.onSort(this);
    }


    public interface SortHandler extends EventHandler {
        void onSort(SortEvent event);
    }

    public interface HasSortHandler extends HasHandlers {
        HandlerRegistration addSortHandler(SortHandler handler);
    }
}