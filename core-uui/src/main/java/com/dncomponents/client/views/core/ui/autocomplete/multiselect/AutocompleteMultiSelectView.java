package com.dncomponents.client.views.core.ui.autocomplete.multiselect;

import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.autocomplete.BaseAutocompleteView;

/**
 * @author nikolasavic
 */
public interface AutocompleteMultiSelectView<M> extends BaseAutocompleteView<M> {

    void clearItems();

    void addItem(IsElement item);

    AutocompleteMultiSelectItemView getAutocompleteMultiSelectItemView();

}