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

package com.dncomponents.client.components.table.header.filter;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.CellEditor;
import com.dncomponents.client.components.core.DefaultCellEditor;
import com.dncomponents.client.components.textbox.*;
import com.dncomponents.client.views.core.ui.table.headers.FilterPanelView;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class FilterUtil {

    public static boolean isEmptyComparator(Comparator comparator) {
        return (comparator instanceof FilterUtil.EmptyComparator ||
                comparator instanceof FilterUtil.NonEmptyComparator);
    }

    //TEXT
    public static final List<Comparator<String, String>> textComparators = Arrays.asList(
            new StringContainsComparator(),
            new StringEqualsComparator(),
            new StringNotEqualsComparator(),
            new StringStartsWithComparator(),
            new StringEndsWithComparator(),
            new EmptyComparator<>(),
            new NonEmptyComparator<>()
    );

    //Collections
    public static final List<Comparator> collectionComparators = Arrays.asList(
            new IsComparator(),
            new IsNotComparator(),
            new IsAnyOfComparator(),
            new IsNonOfComparator(),
            new EmptyComparator(),
            new NonEmptyComparator<>()
    );


    public static final class IsComparator extends Comparator {
        public IsComparator() {
            super("Is");
        }

        @Override
        public boolean test(Object o, Object o2) {
            return Objects.equals(o, o2);
        }
    }

    public static final class IsNotComparator extends Comparator {
        public IsNotComparator() {
            super("Is not");
        }

        @Override
        public boolean test(Object o, Object o2) {
            return !Objects.equals(o, o2);
        }
    }

    public static final class IsAnyOfComparator extends Comparator<Object, List<Object>> {
        public IsAnyOfComparator() {
            super("Is any of");
        }

        @Override
        public boolean test(Object o, List<Object> objects) {
            return objects.contains(o);
        }
    }

    public static final class IsNonOfComparator extends Comparator<Object, List<Object>> {
        public IsNonOfComparator() {
            super("Is non of");
        }

        @Override
        public boolean test(Object o, List<Object> objects) {
            return !objects.contains(o);
        }

    }
    //end collections

    //END TEXT
    //BOOLEAN
    public static List<Comparator<Boolean, Boolean>> booleanComparators = Arrays.asList(
            new BooleanIsComparator(),
            new EmptyComparator<>(),
            new NonEmptyComparator<>()
    );
    //NUMBER
    //TODO i18n
    public static List<Comparator<Number, Number>> numberComparators = Arrays.asList(
            new NumberEqualsComparator(),
            new NumberNotEqualsComparator(),
            new NumberLessThanComparator(),
            new NumberGreaterThanComparator(),
            new NumberGreaterThanOrEqualsComparator(),
            new NumberLessThanOrEqualsComparator(),
            new EmptyComparator<>(),
            new NonEmptyComparator<>()
    );

    //DATE
    public static List<Comparator<Date, Date>> dateComparators = Arrays.asList(
            new DateIsComparator(),
            new EmptyComparator<>(),
            new NonEmptyComparator<>()
    );

    public static final class DateIsComparator extends Comparator<Date, Date> {
        public DateIsComparator() {
            super("is");
        }

        @Override
        public boolean test(Date o, Date userEnteredValue) {
            return o != null && o.equals(userEnteredValue);
        }
    }

    //end DATE
    public static <M> CellEditor<M> getComponent(Class clzz) {
        if (clzz == null)
            throw new NullPointerException("Class argument can't be null!");
        if (clzz == Integer.class) {
            IntegerBox integerBox = new IntegerBox();
            return new DefaultCellEditor(integerBox);
        } else if (clzz == String.class) {
            TextBox tb = new TextBox();
            return new DefaultCellEditor(tb);
        } else if (clzz == Double.class) {
            DoubleBox doubleBox = new DoubleBox();
            return new DefaultCellEditor(doubleBox);
        } else if (clzz == Boolean.class) {
            CheckBox cb = new CheckBox();
            return new DefaultCellEditor(cb);
        } else if (clzz == Long.class) {
            LongBox lb = new LongBox();
            return new DefaultCellEditor(lb);
        } else if (clzz == Date.class) {
            DateBox dateBox = new DateBox();
            return new DefaultCellEditor(dateBox);
        }
        throw new IllegalArgumentException(clzz + " has no defined editor!");
    }

    public static List<? extends Comparator> getComparators(Class clzz) {
        if (clzz == null)
            throw new NullPointerException("Can't be null");
        if (clzz == String.class) {
            return textComparators;
        } else if (clzz == Integer.class || clzz == Double.class || clzz == Float.class || clzz == Long.class) { //TODO maybe flag that column is number
            return numberComparators;
        } else if (clzz == Boolean.class) {
            return booleanComparators;
        } else if (clzz == Date.class) {
            return dateComparators;
        }
        throw new IllegalArgumentException(clzz + " has no defined comparators");
    }

    public static Comparator getDefaultComparator(Class clzz) {
        if (clzz == String.class) {
            return textComparators.get(0);
        } else if (clzz == Integer.class || clzz == Double.class || clzz == Float.class || clzz == Long.class) {
            return numberComparators.get(0);
        } else if (clzz == Boolean.class) {
            return booleanComparators.get(0);
        }
        return null;
    }

    public static final class StringContainsComparator extends Comparator<String, String> {
        public StringContainsComparator() {
            super("contains");
        }

        @Override
        public boolean test(String s, String userEnteredValue) {
            if (s == null) return false;
            if (s == null || userEnteredValue == null) return true;
            return s.toLowerCase().contains(userEnteredValue.toLowerCase());
        }
    }

    public static final class StringEqualsComparator extends Comparator<String, String> {
        public StringEqualsComparator() {
            super("equals");
        }

        @Override
        public boolean test(String s, String userEnteredValue) {
            return (s != null) && s.equals(userEnteredValue);
        }
    }

    public static final class StringNotEqualsComparator extends Comparator<String, String> {
        public StringNotEqualsComparator() {
            super("not equal");
        }

        @Override
        public boolean test(String s, String userEnteredValue) {
            return (s == null) || !s.equals(userEnteredValue);
        }

    }

    public static final class StringStartsWithComparator extends Comparator<String, String> {
        public StringStartsWithComparator() {
            super("starts with");
        }

        @Override
        public boolean test(String s, String userEnteredValue) {
            return (s != null) && s.startsWith(userEnteredValue);
        }

    }

    public static final class StringEndsWithComparator extends Comparator<String, String> {
        public StringEndsWithComparator() {
            super("ends with");
        }

        @Override
        public boolean test(String s, String userEnteredValue) {
            return (s != null) && s.endsWith(userEnteredValue);
        }
    }

//END BOOLEAN

    public static final class BooleanIsComparator extends Comparator<Boolean, Boolean> {
        public BooleanIsComparator() {
            super("is");
        }

        @Override
        public boolean test(Boolean o, Boolean userEnteredValue) {
            return o != null && o.equals(userEnteredValue);
        }
    }

    public static final class NumberEqualsComparator extends Comparator<Number, Number> {
        public NumberEqualsComparator() {
            super("equals");
        }

        @Override
        public boolean test(Number o, Number userEnteredValue) {
            return (o != null) && o.equals(userEnteredValue);
        }

    }

    public static final class NumberNotEqualsComparator extends Comparator<Number, Number> {
        public NumberNotEqualsComparator() {
            super("not equal");
        }

        @Override
        public boolean test(Number o, Number userEnteredValue) {
            return (o != null) && !o.equals(userEnteredValue);
        }
    }

    public static final class NumberLessThanComparator extends Comparator<Number, Number> {
        public NumberLessThanComparator() {
            super("less than");
        }

        @Override
        public boolean test(Number o, Number userEnteredValue) {
            return ((o != null) && ((Comparable) o).compareTo(userEnteredValue) < 0);
        }

    }

    public static final class NumberLessThanOrEqualsComparator extends Comparator<Number, Number> {
        public NumberLessThanOrEqualsComparator() {
            super("less than or equals");
        }

        @Override
        public boolean test(Number o, Number userEnteredValue) {
            return (o != null) && (((Comparable) o).compareTo(userEnteredValue) < 0 || ((Comparable) o).compareTo(userEnteredValue) == 0);
        }

    }

    public static final class NumberGreaterThanComparator extends Comparator<Number, Number> {
        public NumberGreaterThanComparator() {
            super("greater than");
        }

        @Override
        public boolean test(Number o, Number userEnteredValue) {
            return (o != null) && ((Comparable) o).compareTo(userEnteredValue) > 0;
        }

    }

    public static final class NumberGreaterThanOrEqualsComparator extends Comparator<Number, Number> {
        public NumberGreaterThanOrEqualsComparator() {
            super("greater than or equals");
        }

        @Override
        public boolean test(Number o, Number userEnteredValue) {
            return (o != null) && (((Comparable) o).compareTo(userEnteredValue) > 0 || ((Comparable) o).compareTo(userEnteredValue) == 0);
        }

    }
//end NUMBER


    public static final class EmptyComparator<M> extends Comparator<M, M> {
        public EmptyComparator() {
            super("is empty");
        }

        @Override
        public boolean test(M o, M userEnteredValue) {
            return o == null;
        }

    }

    public static final class NonEmptyComparator<M> extends Comparator<M, M> {
        public NonEmptyComparator() {
            super("is not empty");
        }

        @Override
        public boolean test(M o, M userEnteredValue) {
            return o != null;
        }
    }

    public static HasFilterValue getFilterValue(ColumnConfig column, FilterPanelView view) {
        return new FilterPanel(column.getClazz(), view);
    }
}
