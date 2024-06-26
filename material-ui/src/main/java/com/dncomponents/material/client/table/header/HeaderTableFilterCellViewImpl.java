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

package com.dncomponents.material.client.table.header;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.header.SortingDirection;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableFilterCellView;
import elemental2.dom.Event;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;


public class HeaderTableFilterCellViewImpl implements HeaderTableFilterCellView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement textPanel;
    @UiField
    HTMLElement filterIconPanel;
    @UiField
    HTMLElement filterPanelElement;
    @UiField
    HTMLElement sortIconPanel;

    HtmlBinder uiBinder = HtmlBinder.create(HeaderTableFilterCellViewImpl.class, this);

    public HeaderTableFilterCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public HeaderTableFilterCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setFilterPanel(IsElement filterPanel) {
        DomUtil.setVisible(filterIconPanel, false);
        DomUtil.setVisible(sortIconPanel, false);
        filterPanelElement.appendChild(filterPanel.asElement());
        filterPanel.asElement().addEventListener(ClickHandler.TYPE, (ClickHandler) Event::stopPropagation);
    }


    @Override
    public void setFilterIconVisible(boolean b) {
        DomUtil.setVisible(filterIconPanel, b);
    }


    @Override
    public void setSorted(SortingDirection direction) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setActive(boolean b) {

    }

    @Override
    public void setSortPresenter(SortPresenter presenter) {

    }

    @Override
    public void setSortIconText(String iconText) {

    }

    @Override
    public void setGroupOrder(int order) {

    }

    @Override
    public String getText() {
        return textPanel.innerHTML;
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
