package com.dncomponents.material.client.progress;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.MaterialUi;

public class ProgressBuilderConst {
    HtmlBinder binder = HtmlBinder.create(ProgressBuilderConst.class, this);
    @UiField
    String indeterminate;
    @UiField
    String reversed;
    @UiField
    String closed;


    private static ProgressBuilderConst instance;

    public static ProgressBuilderConst getInstance() {
        if (instance == null)
            instance = new ProgressBuilderConst();
        return instance;
    }

    private ProgressBuilderConst() {
        binder.setTemplateElement(MaterialUi.getUi().progressBuilder);
        binder.bind();
    }

}
