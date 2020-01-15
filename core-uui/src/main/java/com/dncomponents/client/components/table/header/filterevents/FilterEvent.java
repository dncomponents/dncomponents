package com.dncomponents.client.components.table.header.filterevents;

import com.dncomponents.client.components.ColumnConfig;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.dncomponents.client.components.table.header.HeaderFiltering;

import java.util.Collection;
import java.util.List;

/**
 * @author nikolasavic
 */
public class FilterEvent extends AbstractModifierEvent<FilterEvent.FilterHandler> {

    /**
     * Handler type.
     */
    private static GwtEvent.Type<FilterHandler> TYPE;

    public FilterEvent(Collection<HeaderFiltering> modifiers) {
        super(modifiers);
    }

    public static GwtEvent.Type<FilterHandler> getType() {
        if (TYPE == null) {
            TYPE = new GwtEvent.Type<>();
        }
        return TYPE;
    }

    @Override
    public List<HeaderFiltering> getModifiers() {
        return (List<HeaderFiltering>) super.getModifiers();
    }

    @Override
    public HeaderFiltering getByColumn(ColumnConfig columnConfig) {
        return (HeaderFiltering) super.getByColumn(columnConfig);
    }

    @Override
    public GwtEvent.Type<FilterHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FilterHandler handler) {
        handler.onFilter(this);
    }


    public interface FilterHandler extends EventHandler {
        void onFilter(FilterEvent event);
    }

    public interface HasFilterHandler extends HasHandlers {
        HandlerRegistration addFilterHandler(FilterHandler handler);
    }

}
