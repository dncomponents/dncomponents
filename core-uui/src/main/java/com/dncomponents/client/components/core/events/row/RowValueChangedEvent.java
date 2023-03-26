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

package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.components.RowTable;
import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

import java.util.List;

public class RowValueChangedEvent<T> extends BaseEvent {

    private RowTable<T> row;
    private List<TableCell<T, ?>> changedCells;

    public RowValueChangedEvent() {
        super(RowValueChangedHandler.TYPE);
    }

    public RowValueChangedEvent(RowTable<T> row) {
        this();
        this.row = row;
    }

    public RowTable<T> getRow() {
        return row;
    }

    public void setRow(RowTable<T> row) {
        this.row = row;
    }

    public List<TableCell<T, ?>> getChangedCells() {
        return changedCells;
    }

    public void setChangedCells(List<TableCell<T, ?>> changedCells) {
        this.changedCells = changedCells;
    }

    public void revertChanges() {
        this.row.revertValueBeforeEdit();
    }

    public static <T> void fire(IsElement source, RowTable<T> row) {
        fire(source.asElement(), row);
    }

    public static <T> void fire(Element source, RowTable<T> row) {
        RowValueChangedEvent<T> event = new RowValueChangedEvent<>(row);
        source.dispatchEvent(event.asEvent());
    }

}
