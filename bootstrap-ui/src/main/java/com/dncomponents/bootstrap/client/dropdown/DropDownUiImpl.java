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

package com.dncomponents.bootstrap.client.dropdown;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemView;
import com.dncomponents.client.views.core.ui.dropdown.DropDownUi;
import com.dncomponents.client.views.core.ui.dropdown.DropDownView;
import elemental2.dom.HTMLTemplateElement;


public class DropDownUiImpl implements DropDownUi {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLTemplateElement dropdown;
    @UiField
    HTMLTemplateElement dropDownItem;

    DropDownView dropDownView;

    HtmlBinder uiBinder = HtmlBinder.create(DropDownUiImpl.class, this);


    public DropDownUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public DropDownItemView getDropDownItemView() {
        return new DropDownItemViewImpl(dropDownItem);
    }

    @Override
    public DropDownView getRootView() {
        if (dropDownView == null)
            dropDownView = new DropDownViewImpl(dropdown);
        return dropDownView;
    }
}
