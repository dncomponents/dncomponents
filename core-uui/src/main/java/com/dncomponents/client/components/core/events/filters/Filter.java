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

package com.dncomponents.client.components.core.events.filters;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.DomUtil;
import elemental2.dom.CustomEvent;
import elemental2.dom.HTMLElement;

import java.util.function.Predicate;


public abstract class Filter<T> implements Predicate<T>, HasFilterHandlers<T> {

    private HTMLElement bus;

    public void fireEvent(CustomEvent event) {
        ensureHandlers().dispatchEvent(event);
    }

    public void fireFilterChange() {
        fireEvent((new FilterEvent(this)).asEvent());
    }

    @Override
    public HandlerRegistration addFilterHandler(FilterHandler<T> handler) {
        return handler.addTo(ensureHandlers());
    }

    protected HTMLElement ensureHandlers() {
        if (bus == null)
            bus = DomUtil.createDiv();
        return bus;
    }
}
