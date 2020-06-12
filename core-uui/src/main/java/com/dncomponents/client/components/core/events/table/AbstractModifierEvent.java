package com.dncomponents.client.components.core.events.table;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.components.table.header.HeaderWithModifiers;
import com.dncomponents.client.dom.handlers.BaseEventListener;

import java.util.Collection;

public abstract class AbstractModifierEvent<H extends BaseEventListener> extends BaseEvent {

    protected final Collection<? extends HeaderWithModifiers> modifiers;

    protected AbstractModifierEvent(String type, Collection<? extends HeaderWithModifiers> modifiers) {
        super(type);
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
