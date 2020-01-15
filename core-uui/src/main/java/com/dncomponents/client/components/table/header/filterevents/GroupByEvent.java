package com.dncomponents.client.components.table.header.filterevents;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.table.header.HeaderGrouping;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

import java.util.LinkedHashSet;

/**
 * @author nikolasavic
 */
public class GroupByEvent extends AbstractModifierEvent<GroupByEvent.GroupByHandler> {

    /**
     * Handler type.
     */
    private static GwtEvent.Type<GroupByHandler> TYPE;

    public GroupByEvent(LinkedHashSet<HeaderGrouping> modifiers) {
        super(modifiers);
    }

    public static GwtEvent.Type<GroupByHandler> getType() {
        if (TYPE == null) {
            TYPE = new GwtEvent.Type<>();
        }
        return TYPE;
    }

    @Override
    public LinkedHashSet<HeaderGrouping> getModifiers() {
        return (LinkedHashSet<HeaderGrouping>) super.getModifiers();
    }

    @Override
    public HeaderGrouping getByColumn(ColumnConfig columnConfig) {
        return (HeaderGrouping) super.getByColumn(columnConfig);
    }

    @Override
    public GwtEvent.Type<GroupByHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(GroupByHandler handler) {
        handler.onGroupBy(this);
    }


    public interface GroupByHandler extends EventHandler {
        void onGroupBy(GroupByEvent event);
    }

    public interface HasGroupByHandler extends HasHandlers {
        HandlerRegistration addGroupByHandler(GroupByHandler handler);
    }

}
