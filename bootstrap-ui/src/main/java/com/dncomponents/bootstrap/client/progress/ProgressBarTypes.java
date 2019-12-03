package com.dncomponents.bootstrap.client.progress;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

/**
 * @author nikolasavic
 */
public enum ProgressBarTypes implements HasStyle {
    STRIPED(() -> ProgressBuilderConst.getInstance().striped),
    ANIMATED(() -> ProgressBuilderConst.getInstance().animated);

    public static EnumLookUp<ProgressBarTypes> lookUp = new EnumLookUp<>(ProgressBarTypes.values());

    StyleCmd styleCmd;

    ProgressBarTypes(StyleCmd style) {
        this.styleCmd = style;
    }

    @Override
    public String getStyle() {
        return styleCmd.getStyle();
    }
}
