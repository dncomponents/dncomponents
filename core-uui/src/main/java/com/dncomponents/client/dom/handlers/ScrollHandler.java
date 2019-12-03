package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface ScrollHandler extends BaseEventListener {

    String TYPE = "scroll";

    void onScroll(Event event);

    @Override
    default void handleEvent(Event evt) {
        onScroll(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}
