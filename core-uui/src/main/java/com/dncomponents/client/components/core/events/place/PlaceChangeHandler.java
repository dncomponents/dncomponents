package com.dncomponents.client.components.core.events.place;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.appview.Place;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import jsinterop.base.Js;

public interface PlaceChangeHandler<T extends Place> extends BaseEventListener {
    void onPlaceChange(PlaceChangeEvent<T> event);

    String TYPE = "placechange";

    @Override
    default void handleEvent(Event evt) {
        onPlaceChange(Js.cast(((CustomEvent) evt).detail));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
