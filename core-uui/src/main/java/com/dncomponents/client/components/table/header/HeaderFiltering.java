package com.dncomponents.client.components.table.header;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.table.header.filter.Comparator;
import com.dncomponents.client.components.table.header.filter.FilterUtil;

import java.util.function.Predicate;

/**
 * @author nikolasavic
 */
public class HeaderFiltering extends HeaderWithModifiers<Predicate> {

    Object userEnteredValue;
    Comparator comparator;
    boolean or;


    public HeaderFiltering(ColumnConfig column) {
        super(column);
    }

    public HeaderFiltering(ColumnConfig column, Object userEnteredValue, Comparator comparator) {
        super(column);
        setUserEnteredValue(userEnteredValue, comparator);
    }

    @Override
    public void copyFrom(HeaderWithModifiers<Predicate> headerWithModifiers) {
        setActiveModifier(headerWithModifiers.getActiveModifier());
    }

    public void setUserEnteredValue(Object userEnteredValue, Comparator comparator) {
        this.userEnteredValue = userEnteredValue;
        this.comparator = comparator;
        if (userEnteredValue == null && !FilterUtil.isEmptyComparator(comparator))
            setActiveModifier(null);//this removes HeaderFiltering
        else if (comparator != null)
            setActiveModifier(o -> comparator.test(column.getFieldGetter().apply(o), userEnteredValue));
    }

    public Comparator getComparator() {
        return comparator;
    }

    public Object getUserEnteredValue() {
        return userEnteredValue;
    }

    public void setUserEnteredValue(Object userEnteredValue) {
        this.userEnteredValue = userEnteredValue;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }
    //TODO uncomment equals for multiple filters!


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeaderFiltering)) return false;
        if (!super.equals(o)) return false;

        HeaderFiltering that = (HeaderFiltering) o;
        if (that.getActiveModifier() == null && this.getActiveModifier() == null)
            return true;
        if (that.getActiveModifier() == null || this.getActiveModifier() == null)
            return false;
        return that.getActiveModifier().equals(this.getActiveModifier());
    }

    //
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getActiveModifier() != null ? getActiveModifier().hashCode() : 0);
        return result;
    }

    public void setOr(boolean or) {
        this.or = or;
    }

    public boolean isOr() {
        return or;
    }
}