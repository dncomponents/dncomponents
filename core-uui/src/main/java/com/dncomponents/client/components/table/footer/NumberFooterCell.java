package com.dncomponents.client.components.table.footer;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.DoubleStream;

/**
 * @author nikolasavic
 */
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
