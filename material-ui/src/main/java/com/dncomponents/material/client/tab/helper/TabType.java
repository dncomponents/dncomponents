package com.dncomponents.material.client.tab.helper;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

public enum TabType implements HasStyle {
    TAB("nav-tabs"), PILL("nav-pills");

    TabType(String style) {
        this.style = style;
    }

    public static EnumLookUp<TabType> lookUp = new EnumLookUp<>(TabType.values());

    String style;

    @Override
    public String getStyle() {
        return style;
    }
}
