package com.dncomponents.bootstrap.client.button;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.components.core.HtmlBinder;

@UiTemplate
public class ButtonBuilderConst {

    @UiField
    String baseStyle;
    //colors
    @UiField
    String btnDanger;
    @UiField
    String btnSuccess;
    @UiField
    String btnWarning;
    @UiField
    String btnPrimary;
    @UiField
    String btnSecondary;
    @UiField
    String btnLight;
    @UiField
    String btnDark;
    @UiField
    String btnInfo;
    @UiField
    String btnLink;
    //    outline
    @UiField
    String btnOutlineSuccess;
    @UiField
    String btnOutlineInfo;
    @UiField
    String btnOutlineWarning;
    @UiField
    String btnOutlineDanger;
    @UiField
    String btnOutlinePrimary;
    @UiField
    String btnOutlineSecondary;
    @UiField
    String btnOutlineLight;
    @UiField
    String btnOutlineDark;
    @UiField
    String btnOutlineLink;
    //    size
    @UiField
    String small;
    @UiField
    String large;
    @UiField
    String block;


    private static ButtonBuilderConst instance;

    public static ButtonBuilderConst getInstance() {
        if (instance == null)
            instance = new ButtonBuilderConst();
        return instance;
    }

    private ButtonBuilderConst() {
        HtmlBinder.get(ButtonBuilderConst.class, this).setTemplateElement(BootstrapUi.getUi().buttonBuilder);
        HtmlBinder.get(ButtonBuilderConst.class, this).bind();
    }

}