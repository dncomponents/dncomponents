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

package com.dncomponents.client.main.components.appviews.dropdown;

import com.dncomponents.client.views.appview.AbstractActivity;
import com.dncomponents.client.views.appview.DefaultActivity;
import com.dncomponents.client.views.appview.Place;
import com.dncomponents.client.views.core.EnumLookUp;

public class DropdownPlace extends Place {
    public Type getType() {
        return type;
    }

    public enum Type {
        BASIC, MULTI_LEVEL;
        public static EnumLookUp<Type> lookUp = new EnumLookUp<>(Type.values());
    }

    private Type type = Type.BASIC;

    public void setType(Type type) {
        this.type = type;
    }

    public DropdownPlace() {
    }

    public DropdownPlace(Type type) {
        this.type = type;
    }

    public static final class DropDownPlaceRegister extends PlaceRegister<DropdownPlace> {

        public static DropDownPlaceRegister instance = new DropDownPlaceRegister();

        private DropDownPlaceRegister() {
        }

        private static final String TOKEN = "dropdown";

        @Override
        public String getHistoryToken() {
            return TOKEN;
        }

        @Override
        public DropdownPlace getPlaceFromToken(String token) {
            DropdownPlace tp = new DropdownPlace();
            String typeString = token.substring(token.indexOf(DIVIDER) + 1);
            Type type = Type.lookUp.getValue(typeString);
            if (type != null)
                tp.setType(type);
            return tp;
        }

        @Override
        public String getTokenFromPlace(DropdownPlace place) {
            return TOKEN + DIVIDER + place.type;
        }

        @Override
        public AbstractActivity getActivity(DropdownPlace place) {
            switch (place.getType()) {
                case MULTI_LEVEL:
                    return new DefaultActivity(DropdownMultiLevelAppView.getInstance(), place);
                case BASIC:
                default:
                    return new DefaultActivity(DropdownAppView.getInstance(), place);
            }
        }

        @Override
        public Class<? extends Place> forPlace() {
            return DropdownPlace.class;
        }

    }
}