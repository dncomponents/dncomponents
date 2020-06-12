package com.dncomponents.client.views.core.ui.autocomplete.multiselect;

import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.core.pcg.View;

/**
 * @author nikolasavic
 */
public interface AutocompleteMultiSelectItemView extends View, MainViewSlots.HasMainViewSlots {
    void addRemoveClickHandler(ClickHandler clickHandler);
}