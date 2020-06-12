package com.dncomponents.client.components.core.events.table;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;
import com.dncomponents.client.components.table.header.HeaderFiltering;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

import java.util.Collection;
import java.util.List;

/**
 * @author nikolasavic
 */
public class FilterEvent extends AbstractModifierEvent<FilterEvent.FilterHandler> {

    public FilterEvent(Collection<HeaderFiltering> modifiers) {
        super(FilterHandler.TYPE, modifiers);
    }

    @Override
    public List<HeaderFiltering> getModifiers() {
        return (List<HeaderFiltering>) super.getModifiers();
    }

    @Override
    public HeaderFiltering getByColumn(ColumnConfig columnConfig) {
        return (HeaderFiltering) super.getByColumn(columnConfig);
    }

    public interface FilterHandler extends BaseEventListener { //todo rename! it has duplicate name.

        void onFilter(FilterEvent event);

        String TYPE = "tablefilter";

        @Override
        default void handleEvent(Event evt) {
            onFilter(Js.cast(((CustomEvent) evt).detail));
        }

        @Override
        default String getType() {
            return TYPE;
        }
    }

    public interface HasFilterHandler extends HasHandlers {
        HandlerRegistration addFilterHandler(FilterHandler handler);
    }

}
