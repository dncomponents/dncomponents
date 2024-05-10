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

package com.dncomponents.client.components.table.header;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.components.TreeGroupBy;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.table.AbstractModifierEvent;
import com.dncomponents.client.components.core.events.table.FilterEvent;
import com.dncomponents.client.components.core.events.table.GroupByEvent;
import com.dncomponents.client.components.core.events.table.SortEvent;
import com.dncomponents.client.components.table.AbstractHeaderCell;
import elemental2.dom.CustomEvent;
import elemental2.dom.HTMLElement;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Manages sorting,grouping and filtering by columns.
 * Fires corresponding event for each column operation.
 * Ui elements that deal with this operations should listen to {@link AbstractModifierEvent} events and update their
 * views accordingly i.e {@link AbstractHeaderCell} cells and bar panels {@link BaseBarPanel}
 */
public class HeaderCellHolder implements
        GroupByEvent.HasGroupByHandler,
        SortEvent.HasSortHandler,
        FilterEvent.HasFilterHandler {

    boolean multiSorting = false;
    boolean multiFiltering = true;
    boolean multiGrouping = true;
    LinkedHashSet<HeaderSorting> headerSortingList = new LinkedHashSet<>();
    List<HeaderFiltering> filtersInOrder = new ArrayList<>();
    LinkedHashSet<HeaderGrouping> groupByInOrder = new LinkedHashSet<>();

    Table<Object> table;
    TreeGroupBy groupBy;

    public HeaderCellHolder(Table table, TreeGroupBy groupBy) {
        this.table = table;
        this.groupBy = groupBy;
    }

    public void addCell(AbstractHeaderCell cell) {
        cell.setHeaderCellHolder(this);
    }

    public void filtered(HeaderFiltering cell) {
        sortOrFilter(cell, filtersInOrder, multiFiltering);
        fireEvent(new FilterEvent(filtersInOrder));
        table.resetScrollTo(0d);
    }

    public void sorted(HeaderSorting cell) {
        sortOrFilter(cell, headerSortingList, multiSorting);
        fireEvent(new SortEvent(headerSortingList));
        table.resetScrollPosition();
    }

    public void group(HeaderGrouping cell) {
        sortOrFilter(cell, groupByInOrder, multiGrouping);
        fireEvent(new GroupByEvent(groupByInOrder));
        table.resetScrollTo(0d);
        table.groupBy.resetScrollTo(0d);
    }

    private <T extends HeaderWithModifiers> void sortOrFilter(T hm, Collection<T> list, boolean multi) {
        if (!list.contains(hm)) {
            if (!multi)
                list.clear();
            list.remove(hm);
            if (hm.getActiveModifier() != null)
                list.add(hm);
        } else if (hm.getActiveModifier() == null)
            list.remove(hm);
        table.drawData();
    }

    /**
     * returns true if group by
     *
     * @return groupBy
     */
    public boolean filterData() {
        clearFiltersAndComparators();
        addFiltersAndComparators();
        if (!groupByInOrder.isEmpty()) {
            table.getGroupBy().groupBy(groupByInOrder);
            return true;
        } else {
            if (groupBy != null)
                groupBy.clear();
//            table.groupBy.ensureVirtualScroll().setEnabled(false);
//            table.drawData();
        }
        return false;
    }

    public boolean hasModifiers() {
        return !headerSortingList.isEmpty() || !filtersInOrder.isEmpty() || !groupByInOrder.isEmpty();
    }

    private void clearFiltersAndComparators() {
        table.getFilters().removeIf(e -> (e instanceof PredicateHc));
        table.getComparators().removeIf(e -> (e instanceof ComparatorHc));
    }

    private void addFiltersAndComparators() {
        if (!filtersInOrder.isEmpty())
            table.addFilter(oneFilter());
        if (!headerSortingList.isEmpty())
            table.addComparator(oneComparator());
    }

    public Comparator oneComparator() {
        return new ComparatorHc(headerSortingList
                .stream()
                .map(HeaderWithModifiers::getActiveModifier)
                .reduce(Comparator::thenComparing)
                .orElse((o11, o21) -> 1));
    }

    public Predicate oneFilter() {
        Predicate predicate = filtersInOrder.get(0).getActiveModifier();
        for (int i = 1; i < filtersInOrder.size(); i++) {
            HeaderFiltering hf = filtersInOrder.get(i);
            if (hf.isOr())
                predicate = predicate.or(hf.getActiveModifier());
            else
                predicate = predicate.and(hf.getActiveModifier());
        }
        return new PredicateHc(predicate);
    }

    public List<ColumnConfig> getGroupByList() {
        return groupByInOrder.
                stream()
                .map(HeaderWithModifiers::getActiveModifier)
                .collect(Collectors.toList());
    }

    protected HTMLElement ensureHandlers() {
        return table.asElement();
    }

    public Table getTable() {
        return table;
    }

    @Override
    public HandlerRegistration addGroupByHandler(GroupByEvent.GroupByHandler handler) {
        return handler.addTo(ensureHandlers());
    }

    @Override
    public HandlerRegistration addSortHandler(SortEvent.SortHandler handler) {
        return handler.addTo(ensureHandlers());
    }

    @Override
    public HandlerRegistration addFilterHandler(FilterEvent.FilterHandler handler) {
        return handler.addTo(ensureHandlers());
    }

    public void setMultiSorting(boolean multiSorting) {
        this.multiSorting = multiSorting;
    }

    public boolean isMultiSorting() {
        return multiSorting;
    }

    @Override
    public void fireEvent(CustomEvent event) {
        ensureHandlers().dispatchEvent(event);
    }
}
