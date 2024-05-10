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

package com.dncomponents.client.components;

import com.dncomponents.client.components.table.BaseTableCell;
import com.dncomponents.client.components.table.footer.FooterCellRenderer;
import com.dncomponents.client.views.core.pcg.cell.CellView;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractFooterCell<T, M> extends BaseTableCell<T, M, CellView> {


    protected FooterCellRenderer<T, M> cellRenderer;

    @Override
    public void draw() {
        if (cellRenderer != null)
            cellRenderer.setValue(cellView.asElement(), this);
    }

    public AbstractFooterCell<T, M> setCellRenderer(FooterCellRenderer<T, M> cellRenderer) {
        this.cellRenderer = cellRenderer;
        return this;
    }

    public List<M> getColumnItems() {
        return getOwner().getRowsData()
                .stream()
                .map(t -> getCellConfig()
                        .getFieldGetter()
                        .apply(t))
                .collect(Collectors.toList());
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getFooterCellView();
    }

}
