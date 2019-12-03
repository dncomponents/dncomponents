package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.MouseEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface MouseEnterHandler extends BaseEventListener {

    String TYPE = "mouseenter";

    void onMouseEnter(MouseEvent mouseEvent);

    @Override
    default void handleEvent(Event evt) {
        onMouseEnter(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}