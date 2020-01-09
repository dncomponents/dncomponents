package com.dncomponents.material.client.progress;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

/**
 * @author nikolasavic
 */
public enum ProgressBarTypes implements HasStyle {

    STANDARD(() -> ""), INDETERMINATE(() -> ProgressBuilderConst.getInstance().indeterminate),
    REVERSED(() -> ProgressBuilderConst.getInstance().reversed);

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
