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

package com.dncomponents.client.main.components.appviews.checkbox;

import com.dncomponents.client.views.appview.AbstractActivity;
import com.dncomponents.client.views.appview.DefaultActivity;
import com.dncomponents.client.views.appview.Place;

public class RadioPlace extends Place {


    public static final class RadioPlaceRegister extends PlaceRegister<RadioPlace> {

        public static RadioPlaceRegister instance = new RadioPlaceRegister();

        private RadioPlaceRegister() {
        }

        private static final String TOKEN = "radio";


        @Override
        public String getHistoryToken() {
            return TOKEN;
        }

        @Override
        public RadioPlace getPlaceFromToken(String token) {
            RadioPlace tp = new RadioPlace();
            return tp;
        }

        @Override
        public String getTokenFromPlace(RadioPlace place) {
            return TOKEN;
        }

        @Override
        public AbstractActivity getActivity(RadioPlace place) {
            return new DefaultActivity(RadioAppView.getInstance(), place);
        }

        @Override
        public Class<? extends Place> forPlace() {
            return RadioPlace.class;
        }

    }
}