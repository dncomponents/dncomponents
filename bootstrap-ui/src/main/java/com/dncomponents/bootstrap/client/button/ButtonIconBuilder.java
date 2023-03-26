/*
 * Copyright 2023 dncomponents
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

public class ButtonIconBuilder {

    private static final String BASE_STYLE = ButtonBuilderConst.getInstance().baseStyle;

    private FontAwesome iconType;
    private FontAwesomeSize iconSize;
    private ButtonColorType colorType;
    private ButtonOutlineColorType outlineColorType;
    private ButtonSize size;
    private HTMLTemplateElement templateElement;
    //
    protected static final String colorTypeId = "color";
    protected static final String outlineColorTypeId = "coloroutline";
    protected static final String sizeId = "size";
    protected static final String iconTypeId = "icontype";
    protected static final String iconSizeId = "iconsize";


    public ButtonIconBuilder() {
    }


    public ButtonIconBuilder iconSize(FontAwesomeSize iconSize) {
        this.iconSize = iconSize;
        return this;
    }

    public ButtonIconBuilder color(ButtonColorType buttonColorType) {
        this.colorType = buttonColorType;
        return this;
    }

    public ButtonIconBuilder outlineColor(ButtonOutlineColorType outlineColorType) {
        this.outlineColorType = outlineColorType;
        return this;
    }

    public ButtonIconBuilder size(ButtonSize buttonSize) {
        this.size = buttonSize;
        return this;
    }

    public ButtonIconBuilder iconType(FontAwesome iconType) {
        this.iconType = iconType;
        return this;
    }

    public ButtonIconBuilder template(HTMLTemplateElement templateElement) {
        this.templateElement = templateElement;
        return this;
    }

    public static ButtonIconBuilder get() {
        return new ButtonIconBuilder();
    }

    public ButtonIconViewImpl build() {
        if (templateElement == null)
            templateElement = BootstrapUi.getUi().buttonIcon;
        ButtonIconViewImpl iconBootstrapView = new ButtonIconViewImpl(templateElement);
        iconBootstrapView.root.className = BASE_STYLE + HasStyle.appendString(colorType) + HasStyle.appendString(outlineColorType)
                + HasStyle.appendString(size);
        iconBootstrapView.iconPanel.className = HasStyle.appendString(iconType) + HasStyle.appendString(iconSize);

        return iconBootstrapView;
    }
}
