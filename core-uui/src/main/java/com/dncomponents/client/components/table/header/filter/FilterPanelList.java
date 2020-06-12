package com.dncomponents.client.components.table.header.filter;

import com.dncomponents.client.components.autocomplete.Autocomplete;
import com.dncomponents.client.components.autocomplete.AutocompleteMultiSelect;
import com.dncomponents.client.components.core.CellEditor;
import com.dncomponents.client.components.core.DefaultCellEditor;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.table.headers.FilterPanelListView;

import java.util.List;

/**
 * @author nikolasavic
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