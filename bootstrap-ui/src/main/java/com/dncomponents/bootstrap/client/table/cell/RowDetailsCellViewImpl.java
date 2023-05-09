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

package com.dncomponents.bootstrap.client.table.cell;

import com.dncomponents.UiField;
import com.dncomponents.client.components.table.columnclasses.rowexpandercolumn.RowDetailsCellView;
import com.dncomponents.client.components.core.HtmlBinder;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class RowDetailsCellViewImpl extends TableCellViewImpl implements RowDetailsCellView {

    @UiField
    HTMLElement openClosedElement;
    @UiField
    String openStyle;
    @UiField
    String closeStyle;

    HtmlBinder uiBinder = HtmlBinder.create(RowDetailsCellViewImpl.class, this);

    public RowDetailsCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public RowDetailsCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setOpened(boolean b) {
        if (!b)
            openClosedElement.className = openStyle;
        else
            openClosedElement.className = closeStyle;
    }

    @Override
    public HTMLElement getValuePanel() {
        return asElement();
    }
}
