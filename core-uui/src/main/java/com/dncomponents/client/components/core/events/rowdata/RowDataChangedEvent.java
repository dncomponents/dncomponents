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

package com.dncomponents.client.components.core.events.rowdata;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.components.core.events.open.OpenHandler;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class RowDataChangedEvent extends BaseEvent {

    private int count;

    public RowDataChangedEvent() {
        super(RowDataChangedHandler.TYPE);
    }

    public RowDataChangedEvent(int size) {
        this();
        this.count = size;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public static <T> void fire(IsElement source, int count) {
        fire(source.asElement(), count);
    }

    public static <T> void fire(Element source, int count) {
        RowDataChangedEvent event = new RowDataChangedEvent(count);
        source.dispatchEvent(event.asEvent());
    }

}
