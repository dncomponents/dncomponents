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

package com.dncomponents.bootstrap.client.table;

import com.dncomponents.Template;
import com.dncomponents.bootstrap.client.cell.BaseCellViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.table.TableRowView;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;


@Template
public class TableRowViewImpl extends BaseCellViewImpl implements TableRowView {

    HtmlBinder uiBinder = HtmlBinder.create(TableRowViewImpl.class, this);

    public TableRowViewImpl() {
        uiBinder.setTemplateElement(((TableUiImpl) (Ui.get()).getTableUi()).tableRow);
        uiBinder.bind();
    }

    public TableRowViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }


    public TableRowViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public Element addColumn() {
        return null;
    }

    @Override
    public void addRow(Element widget, int columnSize) {
    }

    @Override
    public void addColumn(Element element) {
        asElement().appendChild(element);
    }

    @Override
    public void clearCells() {
        asElement().innerHTML = "";
    }

    @Override
    public HTMLElement getValuePanel() {
        return asElement();
    }
}
