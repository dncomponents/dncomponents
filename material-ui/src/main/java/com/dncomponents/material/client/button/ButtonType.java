package com.dncomponents.material.client.button;


import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

import static com.dncomponents.material.client.button.ButtonBuilderConst.getInstance;


/**
 * @author nikolasavic
 */
public enum ButtonType implements HasStyle {
    TEXT(() -> new String("")),RAISED(() -> getInstance().raised), UNELEVATED(() -> getInstance().unelevated),
    OUTLINED(() -> getInstance().outlined);

    public static EnumLookUp<ButtonType> lookUp = new EnumLookUp<>(ButtonType.values());

    StyleCmd styleCmd;

    ButtonType(StyleCmd styleCmd) {
        this.styleCmd = styleCmd;
    }

    @Override
    public String getStyle() {
        return styleCmd.getStyle();
    }
}