package com.dncomponents.client.components.core.events.table;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;
import com.dncomponents.client.components.table.header.HeaderGrouping;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

import java.util.LinkedHashSet;

/**
 * @author nikolasavic
 */
public class GroupByEvent extends AbstractModifierEvent<GroupByEvent.GroupByHandler> {

    public GroupByEvent(LinkedHashSet<HeaderGrouping> modifiers) {
        super(GroupByHandler.TYPE, modifiers);
    }


    @Override
    public LinkedHashSet<HeaderGrouping> getModifiers() {
        return (LinkedHashSet<HeaderGrouping>) super.getModifiers();
    }

    @Override
    public HeaderGrouping getByColumn(ColumnConfig columnConfig) {
        return (HeaderGrouping) super.getByColumn(columnConfig);
    }

    public interface GroupByHandler extends BaseEventListener {

        void onGroup(GroupByEvent event);

        String TYPE = "tablegroupby";

        @Override
        default void handleEvent(Event evt) {
            onGroup(Js.cast(((CustomEvent) evt).detail));
        }

        @Override
        default String getType() {
            return TYPE;
        }
    }

    public interface HasGroupByHandler extends HasHandlers {
        HandlerRegistration addGroupByHandler(GroupByHandler handler);
    }

}
