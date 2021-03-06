package com.dncomponents.client.components.events;

import com.dncomponents.client.dom.handlers.MouseOutHandler;
import com.dncomponents.client.dom.handlers.MouseOverHandler;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import elemental2.dom.MouseEvent;
import elemental2.dom.MouseEventInit;

/**
 * @author nikolasavic
 */
public class MouseCustomEvents {

    boolean mouseOver;
    boolean firstTimeMouseOver;
    HTMLElement element;

    public MouseCustomEvents(HTMLElement element) {
        this.element = element;
        init();
    }

    private void init() {
        ((MouseOutHandler) mouseEvent -> {
            mouseOver = false;
            DomGlobal.setTimeout(e -> {
                if (!mouseOver) {
                    MouseEventInit mouseEventInit = MouseEventInit.create();
                    mouseEventInit.setBubbles(true);
                    element.dispatchEvent(new MouseEvent("mouseleave", mouseEventInit));
                    firstTimeMouseOver = false;
                }
            }, 20);
        }).addTo(element);

        ((MouseOverHandler) mouseEvent -> {
            if (!firstTimeMouseOver) {
                MouseEventInit mouseEventInit = MouseEventInit.create();
                mouseEventInit.setBubbles(true);
                element.dispatchEvent(new MouseEvent("mouseenter", mouseEventInit));
            }
            mouseOver = true;
            firstTimeMouseOver = true;
        }).addTo(element);
    }

}
