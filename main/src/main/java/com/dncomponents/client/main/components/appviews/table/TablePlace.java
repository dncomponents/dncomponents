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

package com.dncomponents.client.main.components.appviews.table;

import com.dncomponents.client.views.appview.AbstractActivity;
import com.dncomponents.client.views.appview.DefaultActivity;
import com.dncomponents.client.views.appview.Place;
import com.dncomponents.client.views.core.EnumLookUp;

import java.util.Objects;

public class TablePlace extends Place {
    {
        this.placeRegister = TablePlaceRegister.instance;
    }

    public enum Type {
        BASIC, CHECKBOX, COLUMN_VISIBILITY, EDITING, ROW_EXPANDER, FILTERING, GROUP, PAGINATION, SORTING, HEADER_AND_FOOTER;
        public static EnumLookUp<Type> lookUp = new EnumLookUp<>(Type.values());
    }

    private Type type = Type.BASIC;

    public TablePlace() {
    }

    public TablePlace(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TablePlace that = (TablePlace) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(type);
    }

    public static final class TablePlaceRegister extends PlaceRegister<TablePlace> {

        public static TablePlaceRegister instance = new TablePlaceRegister();

        private TablePlaceRegister() {
        }

        private static final String TOKEN = "table";

//        private TableAppView simpleAppView = new TableAppView();

        @Override
        public String getHistoryToken() {
            return TOKEN;
        }

        @Override
        public TablePlace getPlaceFromToken(String token) {
            TablePlace tp = new TablePlace();
            String typeString = token.substring(token.indexOf(DIVIDER) + 1);
            Type type = Type.lookUp.getValue(typeString);
            if (type != null)
                tp.setType(type);
            return tp;
        }

        @Override
        public String getTokenFromPlace(TablePlace place) {
            return TOKEN + DIVIDER + place.type;
        }

        @Override
        public AbstractActivity getActivity(TablePlace place) {
            switch (place.getType()) {
                case BASIC:
                    return new DefaultActivity(TableAppView.getInstance(), place);
                case CHECKBOX:
                    return new DefaultActivity(TableCheckBoxAppView.getInstance(), place);
                case COLUMN_VISIBILITY:
                    return new DefaultActivity(TableColumnVisibilityAppView.getInstance(), place);
                case EDITING:
                    return new DefaultActivity(TableEditingAppView.getInstance(), place);
                case ROW_EXPANDER:
                    return new DefaultActivity(TableExpanderAppView.getInstance(), place);
                case FILTERING:
                    return new DefaultActivity(TableFilteringAppView.getInstance(), place);
                case GROUP:
                    return new DefaultActivity(TableGroupByAppView.getInstance(), place);
                case PAGINATION:
                    return new DefaultActivity(TablePaginationAppView.getInstance(), place);
                case SORTING:
                    return new DefaultActivity(TableSortingAppView.getInstance(), place);
                case HEADER_AND_FOOTER:
                    return new DefaultActivity(TableHeaderAndFooterAppView.getInstance(), place);
                default:
                    return new DefaultActivity(TableAppView.getInstance(), place);
            }
        }

        @Override
        public Class<? extends Place> forPlace() {
            return TablePlace.class;
        }


    }
}