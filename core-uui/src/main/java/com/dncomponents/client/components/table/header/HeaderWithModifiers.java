package com.dncomponents.client.components.table.header;


import com.dncomponents.client.components.ColumnConfig;

/**
 * Base class for sorting, filtering and grouping columns.
 * It is uniquely determined by {@link #column} configuration and {@link #activeModifier}
 * Each column can have only one HeaderWithModifiers object for sorting and grouping and multiple objects for filtering.
 * All logic is done in {@link }
 * <p>
 * To filter, group or sort column add to cell holder {@link HeaderWithModifiers} with column
 * and {@link HeaderWithModifiers#activeModifier}
 * to remove it set {@link HeaderWithModifiers#activeModifier} to {@code null}
 *
 * @author nikolasavic
 */
public abstract class HeaderWithModifiers<T> {

    final ColumnConfig column;

    //Null activeModifier means remove it

    private T activeModifier;


    public HeaderWithModifiers(ColumnConfig column) {
        this.column = column;
    }

    public ColumnConfig getColumn() {
        return column;
    }


    public T getActiveModifier() {
        return activeModifier;
    }

    public void setActiveModifier(T activeModifier) {
        this.activeModifier = activeModifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeaderWithModifiers)) return false;
        HeaderWithModifiers that = (HeaderWithModifiers) o;
        return getColumn().equals(that.getColumn());
    }

    @Override
    public int hashCode() {
        return getColumn().hashCode();
    }

    public abstract void copyFrom(HeaderWithModifiers<T> headerWithModifiers);
}
