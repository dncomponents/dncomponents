package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.MouseEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface DragHandler extends BaseEventListener {

    String TYPE = "drag";

    void onClick(MouseEvent mouseEvent);

    @Override
    default void handleEvent(Event evt) {
        onClick(Js.cast(evt));
    }


    @Override
    default String getType() {
        return TYPE;
    }
}