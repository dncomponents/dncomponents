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

package com.dncomponents.client.components.table.columnclasses.checkboxcolumn;

import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.list.ListTreeMultiSelectionModel;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.KeyDownHandler;
import elemental2.dom.KeyboardEvent;
import elemental2.dom.MouseEvent;

/**
 * Created by nikolasavic
 */
public class TableCellCheckBox extends TableCell {

    public TableCellCheckBox() {
    }

    
    @Override
    protected void setOwner(AbstractCellComponent owner) {
        super.setOwner(owner);
        ((ListTreeMultiSelectionModel) getOwner().getSelectionModel()).setNavigator(false);
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().getCheckbox().addValueChangeHandler(
                (ValueChangeEvent<Boolean> event) -> getOwner().getSelectionModel().setSelected(getModel(), event.getValue(), true));
        getCellView().addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyboardEvent event) {
                if (event.key.equals("space")) {
                    getCellView().getCheckbox().setValue(!getCellView().getCheckbox().getValue(), true);
                }
            }
        });
        getCellView().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                getCellView().getCheckbox().setValue(!getCellView().getCheckbox().getValue(),true);
            }
        });
        getOwner().getSelectionModel().addSelectionHandler(event -> draw());
    }

    //TODO do we need this ?
    @Override
    public void draw() {
        getCellView().getCheckbox().setValue(getOwner().getSelectionModel().isSelected(getModel()), false);
    }

    @Override
    protected void setSelection() {
        setSelectionBase();
    }

    @Override
    public TableCellCheckBoxView getCellView() {
        return (TableCellCheckBoxView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTableCellCheckBoxView();
    }
}
