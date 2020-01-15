package com.dncomponents.bootstrap.client.autocomplete.multiselect;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectItemView;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectUi;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class AutocompleteMultiSelectUiImpl<T> implements AutocompleteMultiSelectUi<T> {

    @UiField
    HTMLTemplateElement itemView;
    @UiField
    HTMLTemplateElement rootView;

    HtmlBinder uiBinder = HtmlBinder.get(AutocompleteMultiSelectUiImpl.class, this);

    public AutocompleteMultiSelectUiImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public AutocompleteMultiSelectUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public AutocompleteMultiSelectItemView getAutocompleteMultiSelectItemView() {
        return new AutocompleteMultiSelectItemViewImpl(itemView);
    }

    AutocompleteMultiSelectView autocompleteMultiSelectView;

    @Override
    public AutocompleteMultiSelectView<T> getRootView() {
        if (autocompleteMultiSelectView == null)
            autocompleteMultiSelectView = new AutocompleteMultiSelectViewImpl(rootView);
        return autocompleteMultiSelectView;
    }
}
