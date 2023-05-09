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
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.columnclasses.editcolumn.TableCellEditView;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class TableCellEditViewImpl extends TableCellViewImpl implements TableCellEditView {

    @UiField
    HTMLButtonElement btnEdit;
    @UiField
    HTMLButtonElement btnSave;
    @UiField
    HTMLButtonElement btnCancel;
    @UiField
    HTMLButtonElement btnDelete;

    HtmlBinder uiBinder = HtmlBinder.create(TableCellEditViewImpl.class, this);

    public TableCellEditViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TableCellEditViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void addOnEditHandler(ClickHandler c) {
        DomUtil.addHandler(btnEdit, c);
    }

    @Override
    public void addOnCancelHandler(ClickHandler c) {
        DomUtil.addHandler(btnCancel, c);
    }

    @Override
    public void addOnSaveHandler(ClickHandler c) {
        DomUtil.addHandler(btnSave, c);
    }

    @Override
    public void addOnDeleteHandler(ClickHandler c) {
        DomUtil.addHandler(btnDelete, c);
    }

    @Override
    public void setEditMode(boolean b) {
        DomUtil.setVisible(btnSave, b);
        DomUtil.setVisible(btnCancel, b);
        DomUtil.setVisible(btnEdit, !b);
        DomUtil.setVisible(btnDelete, !b);
    }
}
