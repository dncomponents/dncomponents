package com.dncomponents.client.components.core.events.table;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;
import com.dncomponents.client.components.table.header.HeaderSorting;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author nikolasavic
 */
public class SortEvent extends AbstractModifierEvent<SortEvent.SortHandler> {

    public SortEvent(LinkedHashSet<HeaderSorting> modifiers) {
        super(SortHandler.TYPE, modifiers);
    }


    @Override
    public HeaderSorting getByColumn(ColumnConfig columnConfig) {
        return (HeaderSorting) super.getByColumn(columnConfig);
    }

    @Override
    public LinkedHashSet<HeaderSorting> getModifiers() {
        return (LinkedHashSet<HeaderSorting>) super.getModifiers();
    }

    public interface SortHandler extends BaseEventListener {

        void onSort(SortEvent event);

        String TYPE = "tablesort";

        @Override
        default void handleEvent(Event evt) {
            onSort(Js.cast(((CustomEvent) evt).detail));
        }

        @Override
        default String getType() {
            return TYPE;
        }
    }

    public interface HasSortHandler extends HasHandlers {
        HandlerRegistration addSortHandler(SortHandler handler);
    }

}
