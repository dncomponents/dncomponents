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
