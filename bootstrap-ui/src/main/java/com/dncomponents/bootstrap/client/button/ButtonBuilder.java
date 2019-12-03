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
        if (colorType == null)
            colorType = ButtonColorType.PRIMARY;
        String style = BASE_STYLE + HasStyle.appendString(colorType) + HasStyle.appendString(outlineColorType)
                + HasStyle.appendString(size);
        buttonBootstrapView.root.className = style;
        return buttonBootstrapView;
    }
}