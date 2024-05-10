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

package com.dncomponents.material.client.textbox;


import com.dncomponents.Template;
import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.MaterialUi;

@Template
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
