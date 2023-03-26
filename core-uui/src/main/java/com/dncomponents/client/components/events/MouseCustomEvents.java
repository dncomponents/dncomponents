/*
 * Copyright 2023 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
