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

package com.dncomponents.client.components.core.events.filters;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

import java.util.function.Predicate;

public class FilterEvent<T> extends BaseEvent {

    Predicate<T> filter;

    public FilterEvent() {
        super(FilterHandler.TYPE);
    }


    public FilterEvent(Predicate<T> filter) {
        this();
        this.filter = filter;
    }

    public Predicate<T> getFilter() {
        return filter;
    }

    public void setFilter(Predicate<T> filter) {
        this.filter = filter;
    }

    public static <T> void fire(Element source, Predicate<T> filter) {
        FilterEvent<T> event = new FilterEvent<>(filter);
        source.dispatchEvent(event.asEvent());
    }

    public static <T> void fire(IsElement source, Predicate<T> filter) {
        fire(source.asElement(), filter);
    }

}
