package com.dncomponents.client.components.popover;

import elemental2.dom.HTMLElement;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class Popper {
    @JsType(isNative = true, name = "?", namespace = JsPackage.GLOBAL)
    public interface Defaults {
        @JsOverlay
        static Popper.Defaults create() {
            return Js.uncheckedCast(JsPropertyMap.of());
        }

        @JsProperty
        String getPlacement();

        @JsProperty
        void setPlacement(String mode);
    }


    public Popper(HTMLElement reference, HTMLElement popper, Defaults obj) {
    }

    public native void update();
    public native void scheduleUpdate();
}

