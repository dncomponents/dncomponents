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

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class RowRemovedEvent<T> extends BaseEvent {

    private BaseCell<T, ?> row;
    private Command revertDeletedCmd;

    public RowRemovedEvent() {
        super(RowRemovedHandler.TYPE);
    }

    public RowRemovedEvent(BaseCell<T, ?> row) {
        this();
        this.row = row;
    }

    public BaseCell<T, ?> getRow() {
        return row;
    }

    public void setRow(BaseCell<T, ?> row) {
        this.row = row;
    }

    public void revertDeletedRow() {
        if (this.revertDeletedCmd != null)
            this.revertDeletedCmd.execute();
    }

    public static <T> void fire(IsElement source, BaseCell<T, ?> row, Command cmd) {
        fire(source.asElement(), row, cmd);
    }


    public static <T> void fire(Element source, BaseCell<T, ?> row, Command cmd) {
        RowRemovedEvent<T> event = new RowRemovedEvent<>(row);
        event.revertDeletedCmd = cmd;
        source.dispatchEvent(event.asEvent());
    }

}
