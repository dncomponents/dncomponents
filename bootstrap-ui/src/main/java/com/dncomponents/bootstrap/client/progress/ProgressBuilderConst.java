package com.dncomponents.bootstrap.client.progress;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.bootstrap.client.BootstrapUi;

public class ProgressBuilderConst {
    @UiField
    String striped;
    @UiField
    String animated;
    @UiField
    String success;
    @UiField
    String info;
    @UiField
    String warning;
    @UiField
    String danger;

    private static ProgressBuilderConst instance;

    public static ProgressBuilderConst getInstance() {
        if (instance == null)
            instance = new ProgressBuilderConst();
        return instance;
    }

    private ProgressBuilderConst() {
        HtmlBinder.get(ProgressBuilderConst.class, this).setTemplateElement(BootstrapUi.getUi().progressBuilder);
        HtmlBinder.get(ProgressBuilderConst.class, this).bind();
    }

}
