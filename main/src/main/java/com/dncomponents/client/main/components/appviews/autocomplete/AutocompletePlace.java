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

package com.dncomponents.client.main.components.appviews.autocomplete;

import com.dncomponents.client.views.appview.AbstractActivity;
import com.dncomponents.client.views.appview.DefaultActivity;
import com.dncomponents.client.views.appview.Place;
import com.dncomponents.client.views.core.EnumLookUp;

public class AutocompletePlace extends Place {

    public enum Type {
        LIST, TREE;
        public static EnumLookUp<Type> lookUp = new EnumLookUp<>(Type.values());
    }

    private Type type = Type.LIST;

    public AutocompletePlace() {
    }

    public AutocompletePlace(Type type) {
        this.type = type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public static final class AutocompletePlaceRegister extends PlaceRegister<AutocompletePlace> {

        public static AutocompletePlaceRegister instance = new AutocompletePlaceRegister();

        private AutocompletePlaceRegister() {
        }

        private static final String TOKEN = "autocomplete";

        @Override
        public String getHistoryToken() {
            return TOKEN;
        }

        @Override
        public AutocompletePlace getPlaceFromToken(String token) {
            AutocompletePlace tp = new AutocompletePlace();
            String typeString = token.substring(token.indexOf(DIVIDER) + 1);
            Type type = Type.lookUp.getValue(typeString);
            if (type != null)
                tp.setType(type);
            return tp;
        }

        @Override
        public String getTokenFromPlace(AutocompletePlace place) {
            return TOKEN + DIVIDER + place.type;
        }

        @Override
        public AbstractActivity getActivity(AutocompletePlace place) {
            switch (place.getType()) {
                case LIST:
                    return new DefaultActivity(AutocompleteListAppView.getInstance(), place);
                case TREE:
                    return new DefaultActivity(AutocompleteTreeAppView.getInstance(), place);
                default:
                    return null;
            }
        }

        @Override
        public Class<? extends Place> forPlace() {
            return AutocompletePlace.class;
        }

    }
}