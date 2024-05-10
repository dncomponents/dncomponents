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

package com.dncomponents.client.components.core.events.validator;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;

import java.util.List;


public class ValidationEvent<T> extends BaseEvent {

    private T value;
    private List<String> errors;
    private String error;

    public ValidationEvent() {
        super(ValidationHandler.TYPE);
    }

    public ValidationEvent(T value, List<String> errors) {
        this();
        this.value = value;
        this.errors = errors;
    }

    public ValidationEvent(T value, String error) {
        this();
        this.value = value;
        this.error = error;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<String> getErrors() {
        return errors;
    }

    public static <T> void fire(IsElement source, T value, List<String> errors) {
        ValidationEvent<T> event = new ValidationEvent<>(value, errors);
        source.asElement().dispatchEvent(event.asEvent());
    }

    public static <T> void fire(IsElement source, T value, String error) {
        ValidationEvent<T> event = new ValidationEvent<>(value, error);
        source.asElement().dispatchEvent(event.asEvent());
    }

    public String getFirstError() {
        if (errors != null && !errors.isEmpty())
            return errors.get(0);
        return null;
    }
}
