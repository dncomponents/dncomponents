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

package com.dncomponents.material.client.button;


import com.dncomponents.Template;
import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.MaterialUi;

@Template
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
