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

package com.dncomponents.material.client.table.group;

import com.dncomponents.Template;
import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.core.ui.table.ParentTableTreeCellView;
import com.dncomponents.material.client.tree.basic.TreeCellParentViewImpl;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;


@Template
public class ParentTableTreeCellViewImpl extends TreeCellParentViewImpl implements ParentTableTreeCellView {

    @UiField
    HTMLElement openCloseCell;

    HtmlBinder uiBinder = HtmlBinder.create(ParentTableTreeCellViewImpl.class, this);

    public ParentTableTreeCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public ParentTableTreeCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public void setSpan(int colNumb) {
        openCloseCell.setAttribute("colspan", colNumb + "");
    }

    @Override
    public void setShift(int level) {
        double space = level * 10 + 5;
        DomUtil.setPaddingLeft(openCloseCell, space + "px");
    }
}
