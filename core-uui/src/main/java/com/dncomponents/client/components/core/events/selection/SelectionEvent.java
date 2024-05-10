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

package com.dncomponents.client.components.core.events.selection;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class SelectionEvent<T> extends BaseEvent {

    private T item;

    public SelectionEvent() {
        super(SelectionHandler.TYPE);
    }

    public SelectionEvent(T item) {
        this();
        this.item = item;
    }

    public T getSelectedItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public static <T> void fire(IsElement source, T item) {
        fire(source.asElement(), item);
    }

    public static <T> void fire(HasSelectionHandlers<T> source, T item) {
        SelectionEvent<T> event = new SelectionEvent<>(item);
        source.fireEvent(event.asEvent());
    }

    public static <T> void fire(Element source, T item) {
        SelectionEvent<T> event = new SelectionEvent<>(item);
        source.dispatchEvent(event.asEvent());
    }

}
