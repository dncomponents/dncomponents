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


import com.dncomponents.client.components.table.AbstractHeaderCell;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableTextCellView;

/**
 * Header cell that displays text.
 */
public class HeaderTableTextCell extends AbstractHeaderCell {

    private String text;

    public HeaderTableTextCell() {
    }

    public HeaderTableTextCell(String text) {
        setText(text);
    }

    public HeaderTableTextCell(HeaderTableTextCellView headerCellView) {
        super(headerCellView);
    }

    @Override
    public void draw() {
        getCellView().setText(text == null ? getCellConfig().getName() : text);
    }

    public HeaderTableTextCell setText(String columnName) {
        this.text = columnName;
        return this;
    }

    public String getText() {
        return text;
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getHeaderTableTextCellView();
    }

    @Override
    protected HeaderTableTextCellView getCellView() {
        return (HeaderTableTextCellView) super.getCellView();
    }

}
