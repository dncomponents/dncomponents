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

import com.dncomponents.client.components.core.RowDetailsRenderer;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.table.columnclasses.rowexpandercolumn.RowDetailsCellView;
import com.dncomponents.client.dom.handlers.ClickHandler;
import elemental2.dom.HTMLElement;


public class TableCellRowExpander<T, M> extends TableCell<T, M> {

    public static boolean singleExpand = true;
    boolean toggle = false;
    HTMLElement insertedRow;

    {
        setEditable(false);
    }


    private RowDetailsRenderer<T> rowDetailsRenderer;

    public TableCellRowExpander() {
    }


    @Override
    public void draw() {
        toggle = getOwner().rowExpanderList.contains(getModel());
        getCellView().setOpened(toggle);
        if (toggle)
            addRowDetailsPanel();
        else
            removeRowDetailsPanel();
    }

    @Override
    protected void renderView() {

    }

    @Override
    protected void bind() {
        super.bind();
        ((ClickHandler) mouseEvent -> {
            toggle = !toggle;
            if (singleExpand)
                removeAllExpandedTableRows();
            if (toggle) {
                getOwner().rowExpanderList.add(getModel());
                addRowDetailsPanel();
            } else {
                getOwner().rowExpanderList.remove(getModel());
                removeRowDetailsPanel();
            }
        }).addTo(asElement());
    }

    private void removeAllExpandedTableRows() {
        for (RowTable<T> row : getOwner().getRowCells(getOwner().rowExpanderList)) {
            if (row != null)
                for (TableCell cell : row.cells) {
                    if (cell instanceof TableCellRowExpander) {
                        ((TableCellRowExpander) cell).removeRowDetailsPanel();
                    }
                }
        }
        getOwner().rowExpanderList.clear();
    }

    private void setOpened(boolean b) {
        this.toggle = b;
        getCellView().setOpened(b);
    }

    private void addRowDetailsPanel() {
        insertedRow = getOwner()
                .getView()
                .getRootView()
                .insertAfter(getRowTable(), getOwner().getColumnConfigs().size());
        rowDetailsRenderer.render(model, insertedRow);
        setOpened(true);
    }

    private void removeRowDetailsPanel() {
        if (insertedRow != null)
            insertedRow.remove();
        setOpened(false);
    }

    @Override
    public RowDetailsCellView getCellView() {
        return (RowDetailsCellView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTableCellRowExpanderView();
    }

    public TableCellRowExpander<T, M> setRowDetailsRenderer(RowDetailsRenderer<T> rowDetailsPanel) {
        this.rowDetailsRenderer = rowDetailsPanel;
        return this;
    }

}
