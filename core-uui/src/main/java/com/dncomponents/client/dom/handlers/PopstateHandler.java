package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.PopStateEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface PopstateHandler extends BaseEventListener {

    String TYPE = "popstate";

    void onPopStateChanged(PopStateEvent event);

    @Override
    default void handleEvent(Event evt) {
        onPopStateChanged(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }

}
