package com.dncomponents.bootstrap.client.button;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

import static com.dncomponents.bootstrap.client.button.ButtonBuilderConst.getInstance;

/**`
 * @author nikolasavic
 */
public enum ButtonColorType implements HasStyle {
    DANGER(()->getInstance().btnDanger), DARK(()->getInstance().btnDark), INFO(()->getInstance().btnInfo),
    LIGHT(()->getInstance().btnLight), LINK(()->getInstance().btnLink),
    PRIMARY(()->getInstance().btnPrimary), SECONDARY(()->getInstance().btnSecondary),
    SUCCESS(()->getInstance().btnSuccess), WARNING(()->getInstance().btnWarning);

    public static EnumLookUp<ButtonColorType> lookUp = new EnumLookUp<>(ButtonColorType.values());

    StyleCmd styleCmd;

    ButtonColorType(StyleCmd styleCmd) {
        this.styleCmd = styleCmd;
    }

    public String getStyle() {
        return styleCmd.getStyle();
    }
}
