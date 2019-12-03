package com.dncomponents.client.components.textbox;

import java.text.ParseException;

/**
 * @author nikolasavic
 *         <p>
 *         An object that implements this interface  usually
 *         implements {@link com.google.gwt.user.client.ui.HasValue}
 *         Parses value from view without setting actuall value to component.
 *         Used mainly for validation.
 */
public interface HasValueParser<T> {

    /**
     * Return the parsed value, or null if the field is empty.
     *
     * @throws ParseException if the value cannot be parsed
     */
    T getValueOrThrow() throws ParseException;
}
