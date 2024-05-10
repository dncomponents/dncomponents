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

package com.dncomponents.client.components.table;


import com.dncomponents.client.components.table.header.HeaderCellHolder;
import com.dncomponents.client.views.core.pcg.cell.CellView;


public abstract class AbstractHeaderCell<T> extends BaseTableCell<T, Object, CellView> {

    protected HeaderCellHolder headerCellHolder;

    public AbstractHeaderCell() {
    }

    public AbstractHeaderCell(CellView headerCellView) {
        super(headerCellView);
    }


    public int getColumn() {
        return getOwner().getColumnConfigs().indexOf(cellConfig);
    }

    public void setHeaderCellHolder(HeaderCellHolder cellHolder) {
        this.headerCellHolder = cellHolder;
    }

}
