package com.dncomponents.material.client.textbox;


import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.MaterialUi;

@Component
public class TextBoxBuilderConst {
    HtmlBinder binder = HtmlBinder.create(TextBoxBuilderConst.class, this);
    @UiField
    public String base;
    @UiField
    public String outlined;
    @UiField
    public String fullwidth;
    @UiField
    public String textarea;
    @UiField
    public String disabled;
    @UiField
    public String dense;
    @UiField
    public String withLeadingIcon;
    @UiField
    public String withTrailingIcon;
    @UiField
    public String focused;
    @UiField
    public String floatingLabelAbove;
    @UiField
    public String lineRippleActive;
    //outlined
    @UiField
    public String outlineNotched;
    @UiField
    public String invalid;
    @UiField
    public String noLabel;
    @UiField
    public String invalidTextMessage;
    @UiField
    public String persistent;

    private static TextBoxBuilderConst instance;

    public static TextBoxBuilderConst getInstance() {
        if (instance == null)
            instance = new TextBoxBuilderConst();
        return instance;
    }

    private TextBoxBuilderConst() {
        binder.setTemplateElement(MaterialUi.getUi().textBoxBuilder);
        binder.bind();
    }

}
