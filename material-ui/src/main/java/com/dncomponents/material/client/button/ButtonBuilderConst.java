package com.dncomponents.material.client.button;


import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.MaterialUi;

@UiTemplate
public class ButtonBuilderConst {
    @UiField
    String base;
    @UiField
    String raised;
    @UiField
    String unelevated;
    @UiField
    String outlined;
    @UiField
    String dense;
    @UiField
    String icon;


    private static ButtonBuilderConst instance;

    public static ButtonBuilderConst getInstance() {
        if (instance == null)
            instance = new ButtonBuilderConst();
        return instance;
    }

    private ButtonBuilderConst() {
        HtmlBinder.get(ButtonBuilderConst.class, this).setTemplateElement(MaterialUi.getUi().buttonBuilder);
        HtmlBinder.get(ButtonBuilderConst.class, this).bind();
    }

}