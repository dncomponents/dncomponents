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

package com.dncomponents.material.client.tree.basic;

import com.dncomponents.Template;
import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.tree.BaseTreeCellView;
import com.dncomponents.material.client.cell.BaseCellViewImpl;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;


//tree-item-simple
@Template
public class TreeCellViewImpl extends BaseCellViewImpl implements BaseTreeCellView {

    @UiField
    String activeStyle;
    @UiField
    HTMLElement iconPanel;
    @UiField
    HTMLElement iconParent;
    String icon;


    HtmlBinder uiBinder = HtmlBinder.create(TreeCellViewImpl.class, this);

    public TreeCellViewImpl() {
    }

    public TreeCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TreeCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    public void setActive(boolean b) {
        if (b) asElement().classList.add(activeStyle);
        else asElement().classList.remove(activeStyle);
    }

    @Override
    public void setShift(int level) {
        double space = level * 10 + 5;
        DomUtil.setPaddingLeft(asElement(), space + "px");
    }

    public <C extends BaseTreeCellView> C setIcon(String icon) {
        this.icon = icon;
        if (icon != null && iconParent != null && iconPanel != null) {
            DomUtil.setVisible(iconParent, true);
            Ui.get().getIconRenderer().render(iconPanel, icon);
        }
        return (C) this;
    }

}
