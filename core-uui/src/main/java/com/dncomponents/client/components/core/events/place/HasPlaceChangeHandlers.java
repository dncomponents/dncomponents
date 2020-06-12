package com.dncomponents.client.components.core.events.place;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;
import com.dncomponents.client.views.appview.Place;

public interface HasPlaceChangeHandlers<T extends Place> extends HasHandlers {
    HandlerRegistration addPlaceChangeHandler(PlaceChangeHandler<T> handler);
}
