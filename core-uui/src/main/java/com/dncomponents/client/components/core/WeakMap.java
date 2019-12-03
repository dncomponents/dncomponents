package com.dncomponents.client.components.core;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class WeakMap {
    public native void set(Object o, Object oo);

    public native Object get(Object o);
}
