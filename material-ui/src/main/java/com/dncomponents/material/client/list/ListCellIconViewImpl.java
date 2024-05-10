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

package com.dncomponents.material.client.list;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.material.client.cell.BaseCellViewImpl;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

public class ListCellIconViewImpl extends BaseCellViewImpl {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement iconPanel;
    @UiField
    HTMLElement valuePanel;

    public ListCellIconViewImpl() {
        HtmlBinder uiBinder = HtmlBinder.create(ListCellIconViewImpl.class, this);
        uiBinder.setTemplateElement(MaterialUi.getUi().listCellIconView);
        uiBinder.bind();

    }

    public ListCellIconViewImpl(HTMLTemplateElement templateElement) {
        HtmlBinder uiBinder = HtmlBinder.create(ListCellIconViewImpl.class, this);
        uiBinder.setTemplateElement(templateElement);
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

    public static class Builder {

        private String iconType;
        private HTMLTemplateElement templateElement;

        private Builder() {
        }

        public Builder iconType(String iconType) {
            this.iconType = iconType;
            return this;
        }

        public Builder template(HTMLTemplateElement templateElement) {
            this.templateElement = templateElement;
            return this;
        }

        public static Builder get() {
            return new Builder();
        }

        public ListCellIconViewImpl build() {
            if (templateElement == null)
                templateElement = MaterialUi.getUi().listCellIconView;
            ListCellIconViewImpl listCellIconView = new ListCellIconViewImpl(templateElement);
            listCellIconView.iconPanel.innerHTML = iconType;
            return listCellIconView;
        }
    }


}
