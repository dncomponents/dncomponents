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

package com.dncomponents.client.main.components.appviews.tree;

import com.dncomponents.client.views.appview.AbstractActivity;
import com.dncomponents.client.views.appview.DefaultActivity;
import com.dncomponents.client.views.appview.Place;
import com.dncomponents.client.views.core.EnumLookUp;

import java.util.Objects;

public class TreePlace extends Place {
    public enum Type {
        SIMPLE, CHECKBOX, FILTERING, SELECTION, CRUD, BIG_DATA;
        public static EnumLookUp<Type> lookUp = new EnumLookUp<>(Type.values());
    }

    private Type type = Type.SIMPLE;

    public TreePlace() {
    }

    public TreePlace(Type type) {
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
        TreePlace treePlace = (TreePlace) o;
        return type == treePlace.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(type);
    }

    public static final class TreePlaceRegister extends PlaceRegister<TreePlace> {

        public static TreePlaceRegister instance = new TreePlaceRegister();

        private TreePlaceRegister() {
        }

        private static final String TOKEN = "tree";


        @Override
        public String getHistoryToken() {
            return TOKEN;
        }

        @Override
        public TreePlace getPlaceFromToken(String token) {
            TreePlace tp = new TreePlace();
            String typeString = token.substring(token.indexOf(DIVIDER) + 1);
            Type type = Type.lookUp.getValue(typeString);
            if (type != null)
                tp.setType(type);
            return tp;
        }

        @Override
        public String getTokenFromPlace(TreePlace place) {
            return TOKEN + DIVIDER + place.type;
        }

        @Override
        public AbstractActivity getActivity(TreePlace place) {
            switch (place.getType()) {
                case SIMPLE:
                    return new DefaultActivity(TreeAppView.getInstance(), place);
                case CHECKBOX:
                    return new DefaultActivity(TreeCheckboxAppView.getInstance(), place);
                case SELECTION:
                    return new DefaultActivity(TreeSelectionAppView.getInstance(), place);
                case FILTERING:
                    return new DefaultActivity(TreeFilteringAppView.getInstance(), place);
                case CRUD:
                    return new DefaultActivity(TreeCrudAppView.getInstance(), place);
                case BIG_DATA:
                    return new DefaultActivity(TreeBigDataAppView.getInstance(), place);
                default:
                    return null;
            }
        }

        @Override
        public Class<? extends Place> forPlace() {
            return TreePlace.class;
        }

    }
}