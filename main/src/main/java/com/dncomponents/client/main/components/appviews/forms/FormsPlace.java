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

package com.dncomponents.client.main.components.appviews.forms;

import com.dncomponents.client.views.appview.AbstractActivity;
import com.dncomponents.client.views.appview.DefaultActivity;
import com.dncomponents.client.views.appview.Place;

public class FormsPlace extends Place {


    public static final class FormsPlaceRegister extends PlaceRegister<FormsPlace> {

        public static FormsPlaceRegister instance = new FormsPlaceRegister();

        private FormsPlaceRegister() {
        }

        private static final String TOKEN = "forms";

        @Override
        public String getHistoryToken() {
            return TOKEN;
        }

        @Override
        public FormsPlace getPlaceFromToken(String token) {
            FormsPlace tp = new FormsPlace();
            return tp;
        }

        @Override
        public String getTokenFromPlace(FormsPlace place) {
            return TOKEN;
        }

        @Override
        public AbstractActivity getActivity(FormsPlace place) {
            return new DefaultActivity(FormsAppView.getInstance(), place);
        }

        @Override
        public Class<? extends Place> forPlace() {
            return FormsPlace.class;
        }

    }
}
