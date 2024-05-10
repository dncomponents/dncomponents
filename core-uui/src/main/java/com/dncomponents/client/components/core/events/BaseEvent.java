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

package com.dncomponents.client.components.core.events;

import elemental2.dom.CustomEvent;
import elemental2.dom.CustomEventInit;

public abstract class BaseEvent implements IsEvent {
    protected CustomEvent customEvent;

    public BaseEvent(String type) {
        CustomEventInit init = CustomEventInit.create();
        init.setDetail(this);
        customEvent = new CustomEvent(type, init);
    }

    @Override
    public CustomEvent asEvent() {
        return customEvent;
    }
}
