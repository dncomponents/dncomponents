/*
 * Copyright 2024 dncomponents
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

package com.dncomponents.client.dom.handlers;


import com.dncomponents.client.components.core.events.HandlerRegistration;

import static com.dncomponents.client.dom.DomUtil.asHtmlElement;


public class Handlers {

    public static HandlerRegistration addHandler(Object element, Handler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addClickHandler(Object element, ClickHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addDoubleClickHandler(Object element, DoubleClickHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addDragHandler(Object element, DragHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addKeyDownHandler(Object element, KeyDownHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addKeyUpHandler(Object element, KeyUpHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addLoadHandler(Object element, LoadHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addMouseDownHandler(Object element, MouseDownHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addMouseEnterHandler(Object element, MouseEnterHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addMouseLeaveHandler(Object element, MouseLeaveHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addMouseMoveHandler(Object element, MouseMoveHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addMouseOutHandler(Object element, MouseOutHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addMouseOverHandler(Object element, MouseOverHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addMouseUpHandler(Object element, MouseUpHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addBlurHandler(Object element, OnBlurHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addChangeHandler(Object element, OnChangeHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addFocusHandler(Object element, OnFocusHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addInputHandler(Object element, OnInputHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addPopStateeHandler(Object element, PopstateHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }

    public static HandlerRegistration addScrollHandler(Object element, ScrollHandler handler) {
        return handler.addTo(asHtmlElement(element));
    }


}
