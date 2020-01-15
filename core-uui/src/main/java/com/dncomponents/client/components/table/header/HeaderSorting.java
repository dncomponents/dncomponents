package com.dncomponents.client.components.table.header;


import com.dncomponents.client.components.ColumnConfig;

import java.util.Comparator;

/**
 * @author nikolasavic
 */
public class HeaderSorting extends HeaderWithModifiers<Comparator> implements HasDirection {

    private SortingDirection sortingDirection = SortingDirection.ASCENDING;

    public HeaderSorting(ColumnConfig column) {
        super(column);
        setActiveModifier(column.getComparator());
    }

    @Override
    public void copyFrom(HeaderWithModifiers<Comparator> headerWithModifiers) {
        setActiveModifier(headerWithModifiers.getActiveModifier());
        setSortingDirection(((HeaderSorting) headerWithModifiers).getSortingDirection());
    }

    public HeaderSorting(ColumnConfig column, SortingDirection sortingDirection) {
        this(column);
        setSortingDirection(sortingDirection);
    }

    @Override
    public SortingDirection getSortingDirection() {
        return sortingDirection;
    }

    @Override
    public void setSortingDirection(SortingDirection sortingDirection) {
        setActiveModifier(sortingDirection != null ? sortingDirection == SortingDirection.ASCENDING ?
                column.getComparator() : column.getComparator().reversed() : null);
        this.sortingDirection = sortingDirection;
    }

}