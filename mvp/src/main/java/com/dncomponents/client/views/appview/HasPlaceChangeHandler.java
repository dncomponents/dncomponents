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

package com.dncomponents.client.views.appview;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.value.HasValueChangeHandlers;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;

/**
 * @author nikolasavic
 */
interface HasPlaceChangeHandler extends HasValueChangeHandlers<Place> {
    HandlerRegistration addValueChangeHandler(ValueChangeHandler<Place> handler);

    void goTo(Place place, boolean fireEvent);

    default void goTo(Place place) {
        goTo(place, false);
    }
}