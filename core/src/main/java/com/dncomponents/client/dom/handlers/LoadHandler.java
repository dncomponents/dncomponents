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

package com.dncomponents.client.dom.handlers;

import elemental2.dom.Event;
import elemental2.dom.ProgressEvent;
import jsinterop.base.Js;


public interface LoadHandler extends BaseEventListener {

    String TYPE = "load";

    void onLoadEvent(ProgressEvent progressEvent);

    @Override
    default void handleEvent(Event evt) {
        onLoadEvent(Js.cast(evt));
    }

    @Override
    default String getType() {
        return TYPE;
    }
}
