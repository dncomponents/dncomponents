package com.dncomponents.client.views.core.ui.autocomplete;

import java.util.List;

/**
 * @author nikolasavic
 */
public interface AutocompleteView<M> extends BaseAutocompleteView<M> {
    void setData(List<M> data);

    List<M> getRowsData();

}