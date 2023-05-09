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

package com.dncomponents.bootstrap.client.tabletree;

import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.table.cell.TableCellViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public class TableTreeCellViewImpl extends TableCellViewImpl implements ParentTreeCellView {

    @UiField
    HTMLElement openCloseElement;
    @UiField
    String openStyle;
    @UiField
    String closeStyle;
    @UiField
    String modelSelected;

    HtmlBinder binder = HtmlBinder.create(TableTreeCellViewImpl.class, this);
    @UiField
    public HTMLElement valuePanel;

    public TableTreeCellViewImpl() {
        binder.bind();
    }

    public TableTreeCellViewImpl(String template) {
        super(template);
    }


    @Override
    public HTMLElement getValuePanel() {
        return valuePanel;
    }

    @Override
    public void setOpened(boolean b) {
        if (!b)
            openCloseElement.className = openStyle;
        else
            openCloseElement.className = closeStyle;

    }

    @Override
    public void addOpenCloseHandler(BaseEventListener handler) {
        handler.addTo(openCloseElement);
    }

    @Override
    public void setActive(boolean b) {

    }

    @Override
    public void setPadding(double padding) {

    }
}
