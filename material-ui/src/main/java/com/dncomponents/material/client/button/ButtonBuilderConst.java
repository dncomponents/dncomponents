package com.dncomponents.material.client.button;


import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.MaterialUi;

@Component
public class ButtonBuilderConst {
    HtmlBinder binder = HtmlBinder.create(ButtonBuilderConst.class, this);
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
        binder.setTemplateElement(MaterialUi.getUi().buttonBuilder);
        binder.bind();
    }

}
