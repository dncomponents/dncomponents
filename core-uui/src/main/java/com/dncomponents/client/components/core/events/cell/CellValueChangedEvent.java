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

package com.dncomponents.client.components.core.events.cell;

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class CellValueChangedEvent<T> extends BaseEvent {

    private BaseCell<T, ?> cell;
    private Object oldValue;
    private Object newValue;

    public CellValueChangedEvent() {
        super(CellValueChangedHandler.TYPE);
    }

    public CellValueChangedEvent(BaseCell<T, ?> cell) {
        this();
        this.cell = cell;
    }

    public BaseCell<T, ?> getCell() {
        return cell;
    }

    public void setCell(BaseCell<T, ?> cell) {
        this.cell = cell;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public void revertChanges() {
        this.cell.revertValueBeforeEdit();
    }

    public static <T> void fire(IsElement source, BaseCell<T, ?> cell) {
        fire(source.asElement(), cell);
    }

    public static <T> void fire(Element source, BaseCell<T, ?> cell) {
        CellValueChangedEvent<T> event = new CellValueChangedEvent<>(cell);
        source.dispatchEvent(event.asEvent());
    }
}
