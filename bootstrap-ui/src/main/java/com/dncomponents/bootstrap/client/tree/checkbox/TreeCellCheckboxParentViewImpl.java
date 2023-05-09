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

package com.dncomponents.bootstrap.client.tree.checkbox;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.bootstrap.client.tree.basic.TreeCellParentViewImpl;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.tree.TreeCellCheckboxParentView;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.MouseEvent;

/**
 * @author nikolasavic
 */
@Component
public class TreeCellCheckboxParentViewImpl extends TreeCellParentViewImpl implements TreeCellCheckboxParentView {

    @UiField
    CheckBox checkBox;

    HtmlBinder uiBinder = HtmlBinder.create(TreeCellCheckboxParentViewImpl.class, this);

    public TreeCellCheckboxParentViewImpl() {
    }

    public TreeCellCheckboxParentViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        bind();
    }

    protected void bind() {
        DomUtil.addHandler(checkBox.asElement(), new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                mouseEvent.stopImmediatePropagation();
            }
        });
    }

    @Override
    public HasValue<Boolean> getCheckBox() {
        return checkBox;
    }

    @Override
    public void setIndeterminate(boolean b) {
        checkBox.setIndeterminate(b);
    }

    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        checkBox.setValue(b);
    }
}
