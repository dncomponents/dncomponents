/*
 * Copyright 2024 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
