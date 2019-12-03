package com.dncomponents.client.views.appview;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

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
