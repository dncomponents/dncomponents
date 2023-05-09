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

package com.dncomponents.bootstrap.client.table.header;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.views.core.ui.table.headers.CheckBoxHeaderTableCellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@Component
public class CheckBoxHeaderTableCellViewImpl implements CheckBoxHeaderTableCellView {

    @UiField
    public HTMLElement textPanel;

    @UiField
    CheckBox checkBox;

    @UiField
    HTMLElement root;

    HtmlBinder uiBinder = HtmlBinder.create(CheckBoxHeaderTableCellViewImpl.class, this);

    public CheckBoxHeaderTableCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public CheckBoxHeaderTableCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public HasValue<Boolean> getCheckBox() {
        return checkBox;
    }

    @Override
    public void setText(String text) {
        textPanel.innerHTML = text;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
