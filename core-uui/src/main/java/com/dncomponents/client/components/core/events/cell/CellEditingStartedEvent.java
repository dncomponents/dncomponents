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

package com.dncomponents.client.components.core.events.cell;

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class CellEditingStartedEvent<T> extends BaseEvent {

    private BaseCell<T, ?> cell;
    private boolean cancel;

    public CellEditingStartedEvent() {
        super(CellEditingStartedHandler.TYPE);
    }

    public CellEditingStartedEvent(BaseCell<T, ?> cell) {
        this();
        this.cell = cell;
    }

    public BaseCell<T, ?> getCell() {
        return cell;
    }

    public void setCell(BaseCell<T, ?> cell) {
        this.cell = cell;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isCancel() {
        return cancel;
    }

    public static <T> void fire(IsElement source, BaseCell<T, ?> cell) {
        fire(source.asElement(), cell);
    }

    public static <T> void fire(Element source, BaseCell<T, ?> cell) {
        CellEditingStartedEvent<T> event = new CellEditingStartedEvent<>(cell);
        source.dispatchEvent(event.asEvent());
    }

}
