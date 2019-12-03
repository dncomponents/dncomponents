package com.dncomponents.bootstrap.client.button;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

import static com.dncomponents.bootstrap.client.button.ButtonBuilderConst.getInstance;

/**
 * @author nikolasavic
 */
public enum ButtonOutlineColorType implements HasStyle {

    DANGER(() -> getInstance().btnOutlineDanger), DARK(() -> getInstance().btnOutlineDark),
    INFO(() -> getInstance().btnOutlineInfo),
    LIGHT(() -> getInstance().btnOutlineLight), LINK(() -> getInstance().btnOutlineLink),
    PRIMARY(() -> getInstance().btnOutlinePrimary), SECONDARY(() -> getInstance().btnOutlineSecondary),
    SUCCESS(() -> getInstance().btnOutlineSuccess), WARNING(() -> getInstance().btnOutlineWarning);

    public static EnumLookUp<ButtonOutlineColorType> lookUp = new EnumLookUp<>(ButtonOutlineColorType.values());

    StyleCmd styleCmd;

    ButtonOutlineColorType(StyleCmd styleCmd) {
        this.styleCmd = styleCmd;
    }

    public String getStyle() {
        return styleCmd.getStyle();
    }
}
