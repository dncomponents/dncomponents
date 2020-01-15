package com.dncomponents.client.views.core.ui.autocomplete.multiselect;

import com.dncomponents.client.dom.handlers.OnFocusHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.autocomplete.BaseAutocompleteView;

import java.util.List;

/**
 * @author nikolasavic
 */
public interface AutocompleteMultiSelectView<M> extends BaseAutocompleteView<M> {
    void setData(List<M> data);

    List<M> getRowsData();

    void clearItems();

    void addItem(IsElement item);

    void setSearchPanel();

    void addFocusTextBoxHandler(OnFocusHandler focusHandler);
}
