package com.dncomponents.client.components.table.header;


import java.util.Comparator;

class ComparatorHc<M> implements Comparator<M> {
    Comparator<M> comparator;

    public ComparatorHc(Comparator<M> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(M o1, M o2) {
        return comparator.compare(o1, o2);
    }
}
