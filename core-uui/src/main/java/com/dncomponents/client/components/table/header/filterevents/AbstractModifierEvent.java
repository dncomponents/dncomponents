package com.dncomponents.client.components.table.header.filterevents;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.table.header.HeaderWithModifiers;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import java.util.Collection;

/**
 * Base event for three types of events: Filter , Group and Sort.
 *
 * @author nikolasavic
 */
public abstract class AbstractModifierEvent<H extends EventHandler> extends GwtEvent<H> {

    protected final Collection<? extends HeaderWithModifiers> modifiers;

    protected AbstractModifierEvent(Collection<? extends HeaderWithModifiers> modifiers) {
        this.modifiers = modifiers;
    }

    public Collection<? extends HeaderWithModifiers> getModifiers() {
        return modifiers;
    }

    public HeaderWithModifiers getByColumn(ColumnConfig columnConfig) {
        return modifiers
                .stream()
                .filter(hg -> hg.getColumn().equals(columnConfig))
                .findFirst().orElse(null);
    }

}
