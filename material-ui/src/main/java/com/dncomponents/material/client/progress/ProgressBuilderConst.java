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
