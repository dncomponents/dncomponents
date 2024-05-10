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

package com.dncomponents.bootstrap.client.button;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.views.core.HasStyle;
import elemental2.dom.HTMLTemplateElement;

public class ButtonBuilder {

    private static final String BASE_STYLE = ButtonBuilderConst.getInstance().baseStyle;

    private ButtonColorType colorType;
    private ButtonOutlineColorType outlineColorType;
    private ButtonSize size;
    private HTMLTemplateElement templateElement;

    //parser
    protected static final String colorTypeId = "color";
    protected static final String outlineColorTypeId = "coloroutline";
    protected static final String sizeId = "size";

    private ButtonBuilder() {
    }

    public static ButtonBuilder get() {
        return new ButtonBuilder();
    }

    public ButtonBuilder color(ButtonColorType buttonSize) {
        this.colorType = buttonSize;
        return this;
    }

    public ButtonBuilder outlineColor(ButtonOutlineColorType outlineColorType) {
        this.outlineColorType = outlineColorType;
        return this;
    }

    public ButtonBuilder size(ButtonSize buttonSize) {
        this.size = buttonSize;
        return this;
    }

    public ButtonBuilder template(HTMLTemplateElement templateElement) {
        this.templateElement = templateElement;
        return this;
    }

    public ButtonViewImpl build() {
        if (templateElement == null)
            templateElement = BootstrapUi.getUi().button;
        ButtonViewImpl buttonBootstrapView = new ButtonViewImpl(templateElement);
        if (colorType == null && outlineColorType == null)
            colorType = ButtonColorType.PRIMARY;
        String style = BASE_STYLE + HasStyle.appendString(colorType) + HasStyle.appendString(outlineColorType)
                       + HasStyle.appendString(size);
        buttonBootstrapView.root.className = style;
        return buttonBootstrapView;
    }
}
