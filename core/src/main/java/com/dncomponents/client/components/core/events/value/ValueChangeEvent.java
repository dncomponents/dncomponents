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

package com.dncomponents.client.components.core.events.value;


import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;

import java.util.Objects;

public class ValueChangeEvent<T> extends BaseEvent {

    private T value;
    private T oldValue;

    public ValueChangeEvent() {
        super(ValueChangeHandler.TYPE);
    }

    public ValueChangeEvent(T value) {
        this();
        this.value = value;
    }

    public ValueChangeEvent(T value, T oldValue) {
        this();
        this.value = value;
        this.oldValue = oldValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }


    public T getOldValue() {
        return oldValue;
    }

    public static <T> void fire(IsElement source, T item) {
        ValueChangeEvent<T> event = new ValueChangeEvent<>(item);
        source.asElement().dispatchEvent(event.asEvent());
    }

    public static <T> void fire(HasValueChangeHandlers<T> source, T value) {
        ValueChangeEvent<T> event = new ValueChangeEvent<T>(value);
        source.fireEvent(event.asEvent());
    }

    public static <T> void fire(HasValueChangeHandlers<T> source, T value, T oldValue) {
        ValueChangeEvent<T> event = new ValueChangeEvent<T>(value, oldValue);
        source.fireEvent(event.asEvent());
    }

    public static <T> void fireIfNotEqual(HasValueChangeHandlers<T> source, T oldValue, T newValue) {
        if (!Objects.equals(oldValue, newValue)) {
            ValueChangeEvent<T> event = new ValueChangeEvent<T>(newValue, oldValue);
            source.fireEvent(event.asEvent());
        }
    }

}
