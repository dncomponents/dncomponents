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

import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.components.core.HtmlBinder;

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

    HtmlBinder binder = HtmlBinder.create(ButtonBuilderConst.class, this);
    private static ButtonBuilderConst instance;

    public static ButtonBuilderConst getInstance() {
        if (instance == null)
            instance = new ButtonBuilderConst();
        return instance;
    }

    private ButtonBuilderConst() {
        binder.setTemplateElement(BootstrapUi.getUi().buttonBuilder);
        binder.bind();
    }

}
