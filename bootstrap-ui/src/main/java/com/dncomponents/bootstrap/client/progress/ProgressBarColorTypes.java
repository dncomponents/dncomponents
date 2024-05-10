/*
 * Copyright 2024 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.bootstrap.client.progress;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;


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
