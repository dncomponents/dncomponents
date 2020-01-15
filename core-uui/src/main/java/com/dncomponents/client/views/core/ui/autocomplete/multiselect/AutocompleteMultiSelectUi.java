package com.dncomponents.client.views.core.ui.autocomplete.multiselect;

import com.dncomponents.client.views.FocusComponentView;
import com.dncomponents.client.views.core.pcg.ComponentUi;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface AutocompleteMultiSelectUi<T> extends ComponentUi<AutocompleteMultiSelectView<T>>, FocusComponentView {
    AutocompleteMultiSelectItemView getAutocompleteMultiSelectItemView();

    @Override
    default HTMLElement getFocusElement() {
        return getRootView().getFocusElement();
    }

    @Override
    default void setEnabled(boolean enabled) {
        getRootView().setEnabled(enabled);
    }
}
