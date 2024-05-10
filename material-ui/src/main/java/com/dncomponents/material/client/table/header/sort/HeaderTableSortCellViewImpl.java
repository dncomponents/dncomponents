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

package com.dncomponents.material.client.table.header.sort;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.header.SortingDirection;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableSortCellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.MouseEvent;

import static com.dncomponents.client.components.table.header.SortingDirection.ASCENDING;
import static com.dncomponents.client.components.table.header.SortingDirection.DESCENDING;


public class HeaderTableSortCellViewImpl implements HeaderTableSortCellView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement textPanel;
    @UiField
    HTMLElement sortIcon;
    @UiField
    String sortUpStyle;
    @UiField
    String sortDownStyle;

    SortingDirection currentDirection;
    SortPresenter presenter;

    HtmlBinder uiBinder = HtmlBinder.create(HeaderTableSortCellViewImpl.class, this);

    public HeaderTableSortCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }

    public HeaderTableSortCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    private void init() {
        asElement().addEventListener(ClickHandler.TYPE, new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                if (currentDirection == null) {
                    presenter.sort(ASCENDING);
                } else if (currentDirection == ASCENDING) {
                    presenter.sort(DESCENDING);
                } else if (currentDirection == DESCENDING) {
                    presenter.sort(null);
                }
            }
        });
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
    public void setSorted(SortingDirection direction) {
        if (direction == null) {
            sortIcon.className = "";
//            markup.getSortIcon().className = ("fa fa-sort pull-right");
        } else if (direction == ASCENDING) {
            sortIcon.className = sortDownStyle;
        } else if (direction == DESCENDING) {
            sortIcon.className = sortUpStyle;
        }
        currentDirection = direction;
        setActive(currentDirection != null);
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setActive(boolean b) {
        if (b) asElement().className = ("active");
        else asElement().className = ("active");
    }

    @Override
    public void setSortPresenter(SortPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setSortIconText(String iconText) {
        sortIcon.innerHTML = iconText;
    }

    @Override
    public void setGroupOrder(int order) {

    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
