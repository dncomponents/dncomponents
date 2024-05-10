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

package com.dncomponents.client.components.textbox;

import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.validation.ValidationException;

import java.text.ParseException;

/**
 * <p>
 * An object that implements this interface  usually
 * implements {@link HasValue}
 * Parses value from view without setting actual value to the component.
 * Used mainly for validation.
 */
public interface HasValueParser<T> {

    /**
     * Return the parsed value, or null if the field is empty.
     *
     * @throws ParseException if the value cannot be parsed
     */
    T getValueOrThrow() throws ValidationException;
}
