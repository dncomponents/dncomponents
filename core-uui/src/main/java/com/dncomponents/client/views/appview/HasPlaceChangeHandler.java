package com.dncomponents.client.views.appview;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.value.HasValueChangeHandlers;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;

/**
 * @author nikolasavic
 */
interface HasPlaceChangeHandler extends HasValueChangeHandlers<Place> {
    HandlerRegistration addValueChangeHandler(ValueChangeHandler<Place> handler);

    void goTo(Place place, boolean fireEvent);

    default void goTo(Place place) {
        goTo(place, false);
    }
}
