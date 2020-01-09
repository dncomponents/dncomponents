package com.dncomponents.material.client.textbox;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

import static com.dncomponents.material.client.textbox.TextBoxBuilderConst.getInstance;

/**
 * @author nikolasavic
 */
public enum TextBoxType implements HasStyle {
    OUTLINED(() -> getInstance().outlined),
    FILLED(() -> " ");
    public static EnumLookUp<TextBoxType> lookUp = new EnumLookUp<>(TextBoxType.values());

    StyleCmd styleCmd;

    TextBoxType(StyleCmd style) {
        this.styleCmd = style;
    }

    @Override
    public String getStyle() {
        return styleCmd.getStyle();
    }

}