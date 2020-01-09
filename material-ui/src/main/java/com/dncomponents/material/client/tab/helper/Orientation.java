package com.dncomponents.material.client.tab.helper;

import com.dncomponents.client.views.core.EnumLookUp;

public enum Orientation {
    HORIZONTAL, VERTICAL;
    public static EnumLookUp<Orientation> lookUp = new EnumLookUp<>(Orientation.values());

}
