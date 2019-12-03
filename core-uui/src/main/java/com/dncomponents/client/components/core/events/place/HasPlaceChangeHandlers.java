package com.dncomponents.client.components.core.events.place;

import com.dncomponents.client.components.core.events.HasHandlers;
import com.dncomponents.client.views.appview.Place;
import com.google.gwt.event.shared.HandlerRegistration;

public interface HasPlaceChangeHandlers<T extends Place> extends HasHandlers {
    HandlerRegistration addPlaceChangeHandler(PlaceChangeHandler<T> handler);
}
