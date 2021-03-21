package com.dncomponents.client.components.textbox;

import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.validation.ValidationException;

import java.text.ParseException;

/**
 * @author nikolasavic
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
