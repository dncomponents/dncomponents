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

package com.dncomponents.bootstrap.client.list;

import com.dncomponents.Template;
import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.cell.BaseCellViewImpl;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.views.core.ui.list.ListCellCheckBoxView;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;


@Template
public class ListCellCheckBoxViewImpl extends BaseCellViewImpl implements ListCellCheckBoxView {

    @UiField
    HTMLElement root;

    @UiField
    CheckBox checkBox;

    @UiField
    HTMLElement valuePanel;

    public ListCellCheckBoxViewImpl(HTMLTemplateElement listItemCheckbox) {
        HtmlBinder uiBinder = HtmlBinder.create(ListCellCheckBoxViewImpl.class, this);
        uiBinder.setTemplateElement(listItemCheckbox);
        uiBinder.bind();
    }

    @Override
    public void setToValuePanel(Element element) {
        getValuePanel().innerHTML = "";
        getValuePanel().appendChild(element);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public HTMLElement getValuePanel() {
        return valuePanel;
    }

    @Override
    public HasValue<Boolean> getCheckbox() {
        return checkBox;
    }

    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        checkBox.setValue(b);
    }
}
