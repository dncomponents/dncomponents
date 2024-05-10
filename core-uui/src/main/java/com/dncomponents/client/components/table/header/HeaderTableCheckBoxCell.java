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

package com.dncomponents.client.components.table.header;

import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.table.headers.CheckBoxHeaderTableCellView;
import elemental2.dom.MouseEvent;


public class HeaderTableCheckBoxCell extends HeaderTableTextCell {

    public HeaderTableCheckBoxCell() {
    }

    public HeaderTableCheckBoxCell(CheckBoxHeaderTableCellView view) {
        super(view);
    }

    @Override
    protected void bind() {
        getCellView().getCheckBox().addValueChangeHandler((ValueChangeEvent<Boolean> event) -> getOwner().getSelectionModel().selectAll(event.getValue(), true));
        getOwner().getSelectionModel().addSelectionHandler(event -> draw());
        new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                getCellView().getCheckBox().setValue(!getCellView().getCheckBox().getValue(), true);
            }
        }.addTo(getCellView().asElement());

    }


    @Override
    public void draw() {
        getCellView().getCheckBox().setValue(getOwner().getSelectionModel().isAllSelected(), false);
    }

    @Override
    protected CheckBoxHeaderTableCellView getCellView() {
        return (CheckBoxHeaderTableCellView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getCheckBoxHeaderCellView();
    }
}
