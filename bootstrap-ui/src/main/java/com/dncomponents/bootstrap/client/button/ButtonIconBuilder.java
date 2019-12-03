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
