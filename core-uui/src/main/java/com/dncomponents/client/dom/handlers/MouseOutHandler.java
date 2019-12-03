package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.MouseEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface MouseOutHandler extends BaseEventListener {

    String TYPE = "mouseout";

    void onMouseOut(MouseEvent mouseEvent);

    @Override
    default void handleEvent(Event evt) {
        onMouseOut(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}