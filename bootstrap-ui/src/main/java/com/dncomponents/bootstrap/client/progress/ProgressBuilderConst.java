/*
 * Copyright 2023 dncomponents
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
