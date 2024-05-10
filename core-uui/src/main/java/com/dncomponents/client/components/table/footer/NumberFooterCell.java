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

package com.dncomponents.client.components.table.footer;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.DoubleStream;


public class NumberFooterCell<T, N extends Number> extends FooterCell<T, N> {

    private Types types;
    Function<Number, String> df = new Function<Number, String>() {
        @Override
        public String apply(Number number) {
            return number.toString();
        }
    };

    public NumberFooterCell() {
    }

    public NumberFooterCell(Types types, Function<Number, String> df) {
        this(types);
        this.df = df;
    }

    public NumberFooterCell(Types types) {
        this.types = types;
        setCellRenderer((valuePanel, cell) -> {
            Number value = Types.getValue((List<Number>) cell.getColumnItems(), types);
            String formatted = df.apply(value);
            valuePanel.innerHTML = formatted;
        });
    }

    @Override
    public NumberFooterCell<T, N> setCellRenderer(FooterCellRenderer<T, N> cellRenderer) {
        super.setCellRenderer(cellRenderer);
        return this;
    }

    public enum Types {

        AVERAGE, COUNT, SUM, MIN, MAX;

        public static Number getValue(List<Number> numberList, Types type) {
            switch (type) {
                case MIN:
                    return getDoubleStream(numberList)
                            .min()
                            .getAsDouble();
                case MAX:
                    return getDoubleStream(numberList)
                            .max()
                            .getAsDouble();
                case AVERAGE:
                    return getDoubleStream(numberList)
                            .average()
                            .getAsDouble();
                case COUNT:
                    return getDoubleStream(numberList)
                            .count();
                case SUM:
                    return getDoubleStream(numberList)
                            .sum();
                default:
                    return 0;
            }
        }

        private static DoubleStream getDoubleStream(List<Number> numberList) {
            return numberList
                    .stream()
                    .filter(Objects::nonNull)
                    .mapToDouble(a -> a.doubleValue());
        }

    }

}
