package com.dncomponents.bootstrap.client.progress;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

/**
 * @author nikolasavic
 */
public enum ProgressBarColorTypes implements HasStyle {
    SUCCESS(() -> ProgressBuilderConst.getInstance().success), INFO(() -> ProgressBuilderConst.getInstance().info),
    WARNING(() -> ProgressBuilderConst.getInstance().warning), DANGER(() -> ProgressBuilderConst.getInstance().danger);

    public static EnumLookUp<ProgressBarColorTypes> lookUp = new EnumLookUp<>(ProgressBarColorTypes.values());

    StyleCmd styleCmd;

    ProgressBarColorTypes(StyleCmd style) {
        this.styleCmd = style;
    }

    @Override
    public String getStyle() {
        return styleCmd.getStyle();
    }
}
