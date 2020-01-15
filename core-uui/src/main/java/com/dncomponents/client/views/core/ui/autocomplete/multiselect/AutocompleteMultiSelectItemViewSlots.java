package com.dncomponents.client.views.core.ui.autocomplete.multiselect;

import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.ViewSlots;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface AutocompleteMultiSelectItemViewSlots extends ViewSlots {
    interface HasAutocompleteMultiSelectItemViewSlots extends HasViewSlots<AutocompleteMultiSelectItemViewSlots> {
    }

    HTMLElement getMainSlot();
}