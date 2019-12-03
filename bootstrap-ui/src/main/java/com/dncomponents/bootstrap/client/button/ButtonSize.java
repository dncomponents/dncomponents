package com.dncomponents.bootstrap.client.button;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

import static com.dncomponents.bootstrap.client.button.ButtonBuilderConst.getInstance;

/**
 * @author nikolasavic
 */
public enum ButtonSize implements HasStyle {
    LARGE(() -> getInstance().large), SMALL(() -> getInstance().small), BLOCK(() -> getInstance().block);

    public static EnumLookUp<ButtonSize> lookUp = new EnumLookUp<>(ButtonSize.values());

    StyleCmd styleCmd;


    ButtonSize(StyleCmd styleCmd) {
        this.styleCmd = styleCmd;
    }

    @Override
    public String getStyle() {
        return styleCmd.getStyle();
    }

}