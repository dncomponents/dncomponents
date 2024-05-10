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

import com.dncomponents.client.components.autocomplete.Autocomplete;
import com.dncomponents.client.components.autocomplete.AutocompleteMultiSelect;
import com.dncomponents.client.components.core.CellEditor;
import com.dncomponents.client.components.core.DefaultCellEditor;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.table.headers.FilterPanelListView;

import java.util.List;

/**
 * <p>
 * Displays list of comparators for clazz of column.
 */
public class FilterPanelList<T> extends FilterPanel<T> {

    private List<T> data;

    public FilterPanelList(List<T> data) {
        super(Ui.get().getFilterPanelListView());
        this.data = data;
        view.setValueComponent(getSingleCellEdit());
        comparators = FilterUtil.collectionComparators;
        bind();
    }

    @Override
    protected void setValueComponentFromComparator(Comparator comparator) {
        super.setValueComponentFromComparator(comparator);
        if (FilterUtil.isEmptyComparator(comparator)) {
            return;
        }
        if (comparator instanceof FilterUtil.IsAnyOfComparator ||
            comparator instanceof FilterUtil.IsNonOfComparator) {
            view.setValueComponent(getMultiCellEdit());
        } else {
            view.setValueComponent(getSingleCellEdit());
        }
    }

    CellEditor multiCellEdit;

    private CellEditor getMultiCellEdit() {
        if (multiCellEdit == null) {
            AutocompleteMultiSelect autocompleteMultiSelect = new AutocompleteMultiSelect(getView().getAutocompleteMultiSelectUi());
            autocompleteMultiSelect.setRowsData(data);
            multiCellEdit = new DefaultCellEditor(autocompleteMultiSelect);
            multiCellEdit.getHasValue().addValueChangeHandler(e -> onValueChanged());
        }
        return multiCellEdit;
    }

    CellEditor singleCellEdit;

    private CellEditor getSingleCellEdit() {
        if (singleCellEdit == null) {
            Autocomplete<T> autocomplete = new Autocomplete<>(getView().getAutocompleteView(), t -> t + "");
            autocomplete.setRowsData(data);
            singleCellEdit = new DefaultCellEditor(autocomplete);
            singleCellEdit.getHasValue().addValueChangeHandler(e -> onValueChanged());
        }
        return singleCellEdit;
    }

    @Override
    protected FilterPanelListView getView() {
        return (FilterPanelListView) super.getView();
    }
}
