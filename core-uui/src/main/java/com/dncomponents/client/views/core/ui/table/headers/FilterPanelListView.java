package com.dncomponents.client.views.core.ui.table.headers;

import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteView;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectUi;

public interface FilterPanelListView<T> extends FilterPanelView<T> {
    AutocompleteView getAutocompleteView();

    AutocompleteMultiSelectUi getAutocompleteMultiSelectUi();
}
