package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.MouseEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface MouseOverHandler extends BaseEventListener {

    String TYPE = "mouseover";

    void onMouseOver(MouseEvent mouseEvent);

    @Override
    default void handleEvent(Event evt) {
        onMouseOver(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}