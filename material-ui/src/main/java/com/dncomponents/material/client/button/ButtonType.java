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

package com.dncomponents.material.client.button;


import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;

import static com.dncomponents.material.client.button.ButtonBuilderConst.getInstance;


public enum ButtonType implements HasStyle {
    TEXT(() -> new String("")), RAISED(() -> getInstance().raised), UNELEVATED(() -> getInstance().unelevated),
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