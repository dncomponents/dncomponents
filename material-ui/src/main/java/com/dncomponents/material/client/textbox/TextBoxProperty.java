package com.dncomponents.material.client.textbox;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

import static com.dncomponents.material.client.textbox.TextBoxBuilderConst.getInstance;

/**
 * @author nikolasavic
 */
public enum TextBoxProperty implements HasStyle {
    FULL_WIDTH(() -> getInstance().fullwidth),
    DENSE(() -> getInstance().dense);
    public static EnumLookUp<TextBoxType> lookUp = new com.dncomponents.client.views.core.EnumLookUp<>(TextBoxType.values());

    HasStyle.StyleCmd styleCmd;

    TextBoxProperty(HasStyle.StyleCmd style) {
        this.styleCmd = style;
    }

    @Override
    public String getStyle() {
        return styleCmd.getStyle();
    }

}