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

package com.dncomponents.client.main.components.appviews.list;

import com.dncomponents.client.views.appview.AbstractActivity;
import com.dncomponents.client.views.appview.DefaultActivity;
import com.dncomponents.client.views.appview.Place;
import com.dncomponents.client.views.core.EnumLookUp;

import java.util.Objects;

public class ListPlace extends Place {
    public enum Type {
        BASIC, CHECKBOX, CRUD, BIG_DATA, FILTERING, SELECTION, SORTING;
        public static EnumLookUp<Type> lookUp = new EnumLookUp<>(Type.values());
    }

    private Type type = Type.BASIC;

    public ListPlace() {
    }

    public ListPlace(Type type) {
        this.type = type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListPlace listPlace = (ListPlace) o;
        return type == listPlace.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    public static final class ListPlaceRegister extends PlaceRegister<ListPlace> {

        public static ListPlaceRegister instance = new ListPlaceRegister();

        private ListPlaceRegister() {
        }

        private static final String TOKEN = "list";

        @Override
        public String getHistoryToken() {
            return TOKEN;
        }

        @Override
        public ListPlace getPlaceFromToken(String token) {
            ListPlace tp = new ListPlace();
            String typeString = token.substring(token.indexOf(DIVIDER) + 1);
            Type type = Type.lookUp.getValue(typeString);
            if (type != null)
                tp.setType(type);
            return tp;
        }

        @Override
        public String getTokenFromPlace(ListPlace place) {
            return TOKEN + DIVIDER + place.type;
        }

        @Override
        public AbstractActivity getActivity(ListPlace place) {
            switch (place.getType()) {
                case BASIC:
                    return new DefaultActivity(ListAppView.getInstance(), place);
                case CHECKBOX:
                    return new DefaultActivity(ListCheckboxAppView.getInstance(), place);
                case CRUD:
                    return new DefaultActivity(ListCrudAppView.getInstance(), place);
                case BIG_DATA:
                    return new DefaultActivity(ListBigDataAppView.getInstance(), place);
                case FILTERING:
                    return new DefaultActivity(ListFilterAppView.getInstance(), place);
                case SELECTION:
                    return new DefaultActivity(ListSelectionAppView.getInstance(), place);
                case SORTING:
                    return new DefaultActivity(ListSortingAppView.getInstance(), place);
                default:
                    return null;
            }
        }

        @Override
        public Class<? extends Place> forPlace() {
            return ListPlace.class;
        }

    }
}
