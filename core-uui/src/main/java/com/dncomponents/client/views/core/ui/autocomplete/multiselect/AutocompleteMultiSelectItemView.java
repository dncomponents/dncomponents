package com.dncomponents.client.views.core.ui.autocomplete.multiselect;

import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.pcg.View;

/**
 * @author nikolasavic
 */
public interface AutocompleteMultiSelectItemView extends View, AutocompleteMultiSelectItemViewSlots.HasAutocompleteMultiSelectItemViewSlots {
    void addRemoveClickHandler(ClickHandler clickHandler);
}
