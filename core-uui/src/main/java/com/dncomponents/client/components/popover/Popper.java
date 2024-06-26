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

