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

package com.dncomponents.client.main.components.appviews.popover;

import com.dncomponents.client.views.appview.AbstractActivity;
import com.dncomponents.client.views.appview.DefaultActivity;
import com.dncomponents.client.views.appview.Place;

public class PopoverPlace extends Place {

    public static final class PopoverPlaceRegister extends PlaceRegister<PopoverPlace> {

        public static PopoverPlaceRegister instance = new PopoverPlaceRegister();

        private PopoverPlaceRegister() {
        }

        private static final String TOKEN = "popover";

        @Override
        public String getHistoryToken() {
            return TOKEN;
        }

        @Override
        public PopoverPlace getPlaceFromToken(String token) {
            return new PopoverPlace();
        }

        @Override
        public String getTokenFromPlace(PopoverPlace place) {
            return TOKEN;
        }

        @Override
        public AbstractActivity getActivity(PopoverPlace place) {

            return new DefaultActivity(PopoverAppView.getInstance(), place);
        }

        @Override
        public Class<? extends Place> forPlace() {
            return PopoverPlace.class;
        }

    }
}