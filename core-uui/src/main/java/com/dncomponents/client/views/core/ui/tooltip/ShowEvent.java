/*
 * Copyright 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.dncomponents.client.views.core.ui.tooltip;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;

/**
 * Represents a show range event. This logical event should be used when a
 * widget displays a range of values to the user.
 *
 * @param <V> the type of range
 */
public class ShowEvent<V> extends GwtEvent<ShowHandler<V>> {

    /**
     * Handler type.
     */
    private static Type<ShowHandler<?>> TYPE;

    /**
     * Fires a show range event on all registered handlers in the handler manager.
     *
     * @param <V>    the type of range
     * @param <S>    the event source
     * @param source the source of the handlers
     */
    public static <V, S extends HasShowHandlers<V> & HasHandlers> void fire(
            S source) {
        if (TYPE != null) {
            ShowEvent<V> event = new ShowEvent<V>();
            source.fireEvent(event);
        }
    }

    /**
     * Gets the type associated with this event.
     *
     * @return returns the handler type
     */
    public static Type<ShowHandler<?>> getType() {
        if (TYPE == null) {
            TYPE = new Type<>();
        }
        return TYPE;
    }

    /**
     * Creates a new show range event.
     */
    protected ShowEvent() {
    }

    // Because of type erasure, our static type is
    // wild carded, yet the "real" type should use our I param.
    @SuppressWarnings("unchecked")
    @Override
    public final Type<ShowHandler<V>> getAssociatedType() {
        return (Type) TYPE;
    }

    @Override
    protected void dispatch(ShowHandler<V> handler) {
        handler.onShow(this);
    }
}
