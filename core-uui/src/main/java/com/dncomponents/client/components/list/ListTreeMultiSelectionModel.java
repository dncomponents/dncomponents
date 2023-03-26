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

package com.dncomponents.client.components.list;

import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.views.core.ui.list.ListView;
import elemental2.dom.KeyboardEvent;
import elemental2.dom.MouseEvent;

import java.util.List;

/**
 * @author nikolasavic
 */
public class ListTreeMultiSelectionModel<T> extends DefaultMultiSelectionModel<T> {

    private final ListView ownerView;
    protected InvokeType invokeType = InvokeType.ON_CELL_CLICKED;

    protected AbstractCellComponent owner;
    protected Object lastSelectedModelWithoutShiftPressed;
    protected ListTreeCellNavigator cellNavigator;

    public ListTreeMultiSelectionModel(AbstractCellComponent owner, ListView view) {
        this.owner = owner;
        this.ownerView = view;
        setNavigator();
    }

    public void setNavigator(boolean value) {
        if (value)
            setNavigator();
        else
            removeNavigator();
    }

    public void focusCell(BaseCell cell) {
        cellNavigator.setVal(cell.getModel());
    }

    private void setNavigator() {
        cellNavigator = new ListTreeCellNavigator(owner, ownerView);
        cellNavigator.setHandler(new BaseCellNavigator.Handler() {
            @Override
            public void onKeyFocused(Object currentFocusedModel, Object lastFocusedModel, KeyboardEvent event) {
                if (invokeType == InvokeType.ON_FOCUS && selectionMode == SelectionMode.SINGLE) {
                    setSelected((T) currentFocusedModel, true, true);
                } else if (selectionMode == SelectionMode.MULTI && event.shiftKey) {
                    selectRegion(currentFocusedModel);
                }
            }

            @Override
            public void onClickEvent(Object currentFocusedCell, Object lastFocusedCell, MouseEvent event) {
                if (!event.shiftKey)
                    lastSelectedModelWithoutShiftPressed = currentFocusedCell;
                cellClicked(currentFocusedCell, event);
            }

            @Override
            public void onClickEventEquals(Object model) {
//                selectAll(false, false);
                setSelected((T) model, !isSelected((T) model), true);
            }
        });
    }

    private void removeNavigator() {
        if (cellNavigator != null) {
            cellNavigator.removeHandlers();
            cellNavigator = null;
        }
    }

    protected void cellClicked(Object model, MouseEvent mouseEvent) {
        if (mouseEvent != null && (mouseEvent.metaKey || !useMataKeyForSelection)) {
            setSelected((T) model, !isSelected((T) model), true);
        } else if (mouseEvent != null && mouseEvent.shiftKey) {
            selectRegion(model);
        } else {
//            selectAll(false, false);
            setSelected((T) model, !isSelected((T) model), true);
        }
    }

    //todo when this is uncommented it selects item in view properly (e.g sidemenu)
//    //todo but performance are very bed on big data (list big data 300 000 items)
//    @Override
//    public void setSelectedInView(T model, boolean b) {
//        this.getOwner().drawData();
//        final BaseCell rowCell = getOwner().getRowCell(model);
//        if (rowCell != null) {
//            rowCell.draw();
//            rowCell.asElement().focus();
//        }
//    }

    protected void selectRegion(Object model) {
        if (lastSelectedModelWithoutShiftPressed != null) {
//            int lastSelectedRow = lastSelectedModelWithoutShiftPressed.getRow();
            int lastSelectedRow = owner.getRowsFiltered().indexOf(lastSelectedModelWithoutShiftPressed);
//            int selectedRow = cell.getRow();
            int selectedRow = owner.getRowsFiltered().indexOf(model);
            for (int i = Math.min(lastSelectedRow, selectedRow); i <= Math.max(lastSelectedRow, selectedRow); i++)
                setSelected((T) owner.getRowCell(i).getModel(), true, true);
        }
    }

    @Override
    public List<T> getItems() {
        return owner.getRowsData();
    }

    public AbstractCellComponent getOwner() {
        return owner;
    }

    public ListTreeCellNavigator getCellNavigator() {
        if (cellNavigator == null) {
//            if (this instanceof TableSelectionModel) {
//                cellNavigator = new TableCellNavigator(owner);
//            } else
            {
//                cellNavigator = new ListCellNavigator(owner,owner);
//                cellNavigator = new ListCellNavigator(owner);
            }
        }
        return cellNavigator;
    }


    @Override
    public void setSelectionMode(SelectionMode selectionMode) {
        if (selectionMode == SelectionMode.MULTI) {
            setInvokeType(InvokeType.ON_CELL_CLICKED);// multi is only on cell clicked
//            return;  //Can't select multi if invoke type is on_focus
        }
        super.setSelectionMode(selectionMode);
    }

    public InvokeType getInvokeType() {
        return invokeType;
    }

    public void setInvokeType(InvokeType invokeType) {
        if (selectionMode == SelectionMode.MULTI) //multi is always on cell clicked
            return;
        this.invokeType = invokeType;
    }

    boolean useMataKeyForSelection = true;


    /**
     * Use meta key when selecting multiple items. Default to true.
     *
     * @param b
     */
    public void useMetaKeyForSelection(boolean b) {
        this.useMataKeyForSelection = b;
    }


    public enum InvokeType {
        ON_CELL_CLICKED, ON_FOCUS;
    }
}
