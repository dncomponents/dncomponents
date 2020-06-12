package com.dncomponents.client.views.core.ui.autocomplete;

import com.dncomponents.client.components.HasRowsDataList;

/**
 * @author nikolasavic
 */
public interface AutocompleteView<M> extends BaseAutocompleteView<M> {
    @Override
    HasRowsDataList<M> getHasRowsData();
}