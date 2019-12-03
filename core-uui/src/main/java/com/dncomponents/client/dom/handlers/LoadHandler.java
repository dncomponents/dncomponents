package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.ProgressEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface LoadHandler extends BaseEventListener {

    String TYPE = "load";

    void onLoadEvent(ProgressEvent progressEvent);

    @Override
    default void handleEvent(Event evt) {
        onLoadEvent(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}
