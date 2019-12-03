package com.dncomponents.bootstrap.client.tab.helper;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

public enum Orientation implements HasStyle {
    HORIZONTAL(""), VERTICAL("flex-column");

    Orientation(String style) {
        this.style = style;
    }

    public static EnumLookUp<Orientation> lookUp = new EnumLookUp<>(Orientation.values());

    String style;

    @Override
    public String getStyle() {
        return style;
    }
}
