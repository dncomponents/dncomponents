package com.dncomponents.material.client.button;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

/**
 * @author nikolasavic
 */
public enum FontAwesomeSize implements HasStyle {
    EXTRA_SMALL("xs"), SMALL("sm"), LARGE("lg"),
    TWO_TIMES("2x"), THREE_TIMES("3x"), FIVE_TIMES("5x"), SEVENT_TIMES("6x");

    public static EnumLookUp<FontAwesomeSize> lookUp = new EnumLookUp<>(FontAwesomeSize.values());


    String style;

    FontAwesomeSize(String style) {
        this.style = FontAwesome.BASE_STYLE + "-" + style;
    }

    public String getStyle() {
        return style;
    }
}
