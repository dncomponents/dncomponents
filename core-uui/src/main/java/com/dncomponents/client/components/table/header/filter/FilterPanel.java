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

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.CellEditor;
import com.dncomponents.client.views.core.ui.table.headers.FilterPanelView;

import java.util.List;

/**
 * <p>
 * Displays list of comparators for clazz of column.
 */
public class FilterPanel<T> extends BaseComponent<T, FilterPanelView> implements HasFilterValue<T> {

    protected FilterValueHandler<T> filterValueHandler;
    protected List<? extends Comparator> comparators;
    private boolean hideClear;

    protected Comparator currentComparator;
    protected Object currentValue;
    private CellEditor<T> valueComponent;

    public FilterPanel(Class<T> clazz, FilterPanelView view) {
        super(view);
        comparators = FilterUtil.getComparators(clazz);
        view.setValueComponent(clazz);
        valueComponent = view.getValueComponent();
        view.setValueComponent(valueComponent);
        bind();
    }

    protected FilterPanel(FilterPanelView<T> view) {
        super(view);
    }

    protected void setValueComponentFromComparator(Comparator comparator) {
        view.setValueComponent(FilterUtil.isEmptyComparator(comparator) ? null : valueComponent);
    }

    protected void onValueChanged() {
        if (currentValue() != null || FilterUtil.isEmptyComparator(currentComparator())) {
            filterValueHandler.selected(currentValue(), currentComparator());
        }
        view.getValueComponent().getFocusable().setFocus(true);
    }

    protected T currentValue() {
        return (T) view.getValueComponent().getHasValue().getValue();
    }

    protected Comparator currentComparator() {
        return (Comparator) view.getComparatorHasValue().getValue();
    }


    protected void bind() {
        view.getValueComponent().getHasValue().addValueChangeHandler(event -> onValueChanged());
        view.getComparatorHasValue().addValueChangeHandler(event -> {
            Comparator comparator = (Comparator) event.getValue();
            if (currentValue != null || FilterUtil.isEmptyComparator(currentComparator)) {
                filterValueHandler.selected(null, null);
                view.getValueComponent().getHasValue().setValue(null, false);
            } else if (FilterUtil.isEmptyComparator(comparator)) {
                filterValueHandler.selected(null, comparator);
                view.setValueComponent((CellEditor) null);
                return;
            }
            setValueComponentFromComparator(comparator);
            view.getComparatorHasValue().setValue(comparator, false);
            focusValueComponent();
        });
        view.getComparatorHasRowsData().setRowsData(comparators);
        view.getComparatorHasRowsData().drawData();
        view.getComparatorHasValue().setValue(comparators.get(0));
        view.addClearClickHandler(mouseEvent -> {
            filterValueHandler.selected(null, null);
            focusValueComponent();
        });
    }

    private void focusValueComponent() {
        if (view.getValueComponent() != null) {
            view.getValueComponent().getFocusable().setFocus(true);
        }
    }

    public void setValue(Object userEnteredValue, Comparator comparator) {
        this.currentValue = userEnteredValue;
        this.currentComparator = comparator;
        if (!hideClear)
            view.showClearElement(userEnteredValue != null || FilterUtil.isEmptyComparator(comparator));
        view.getValueComponent().getHasValue().setValue(userEnteredValue);
        if (comparator != null) {
            setValueComponentFromComparator(comparator);
        } else {
            if (!view.getComparatorHasRowsData().getRowsData().isEmpty()) {
                view.getComparatorHasValue().setValue(comparators.get(0));
                setValueComponentFromComparator(comparators.get(0));
            }
        }
    }


    @Override
    public void setFilterValueHandler(FilterValueHandler<T> handler) {
        this.filterValueHandler = handler;
    }

    public void hideClearButton() {
        hideClear = true;
        view.showClearElement(false);
    }
}
