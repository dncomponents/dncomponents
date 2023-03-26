/*
 * Copyright 2023 dncomponents
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
