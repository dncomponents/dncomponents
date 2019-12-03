package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.MouseEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface MouseLeaveHandler extends BaseEventListener {

    String TYPE = "mouseleave";

    void onMouseLeave(MouseEvent mouseEvent);

    @Override
    default void handleEvent(Event evt) {
        onMouseLeave(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}