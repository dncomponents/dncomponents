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

package com.dncomponents.material.client.multilevel;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.dropdown.*;
import elemental2.dom.HTMLTemplateElement;


public class DropDownMultiLevelUiImpl implements DropDownMultiLevelUi {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLTemplateElement dropdown;
    @UiField
    HTMLTemplateElement dropdownItem;
    @UiField
    HTMLTemplateElement dropdownItemHasChildren;
    @UiField
    HTMLTemplateElement dropdownPanelHasChildren;

    DropDownView dropDownView;

    HtmlBinder uiBinder = HtmlBinder.create(DropDownMultiLevelUiImpl.class, this);


    public DropDownMultiLevelUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public DropDownItemView getDropDownItemView() {
        return new DropDownItemMultiLevelViewImpl(dropdownItem);
    }

    @Override
    public DropDownItemMultiLevelParentView getDropDownItemMultiLevelParentView() {
        return new DropDownItemMultiLevelParentViewImpl(dropdownItemHasChildren);
    }

    @Override
    public DropDownTreeNodePanelView getDropDownTreeNodePanelView() {
        return new DropDownTreeNodePanelViewImpl(dropdownPanelHasChildren);
    }

    @Override
    public DropDownView getRootView() {
        if (dropDownView == null)
            dropDownView = new DropDownMultilevelViewImpl(dropdown);
        return dropDownView;
    }
}
