package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.MouseEvent;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
public interface DoubleClickHandler extends BaseEventListener {

    String TYPE = "dblclick";

    void onDoubleClick(MouseEvent mouseEvent);

    @Override
    default void handleEvent(Event evt) {
        onDoubleClick(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}