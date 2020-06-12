package com.dncomponents.client.views.core.ui.table.headers;

import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteView;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectView;

public interface FilterPanelListView<T> extends FilterPanelView<T> {
    AutocompleteView getAutocompleteView();

    AutocompleteMultiSelectView getAutocompleteMultiSelectUi();
}
