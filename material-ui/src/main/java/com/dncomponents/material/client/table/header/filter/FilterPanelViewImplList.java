package com.dncomponents.material.client.table.header.filter;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteView;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectUi;
import com.dncomponents.client.views.core.ui.table.headers.FilterPanelListView;
import com.dncomponents.material.client.autocomplete.list.AutocompleteViewImpl;
import com.dncomponents.material.client.autocomplete.multiselect.AutocompleteMultiSelectUiImpl;
import elemental2.dom.HTMLTemplateElement;

public class FilterPanelViewImplList<T> extends FilterPanelViewImpl<T> implements FilterPanelListView<T> {

    private AutocompleteMultiSelectUiImpl autocompleteMultiSelectUi;


    @UiField
    public HTMLTemplateElement accTemplate;
    @UiField
    public HTMLTemplateElement autocompleteMs;


    HtmlBinder uiBinder = HtmlBinder.get(FilterPanelViewImplList.class, this);

    public FilterPanelViewImplList(HTMLTemplateElement templateElement) {
        super(templateElement);
        uiBinder.setTemplateElement(templateElement);
        init();
    }

    private void init() {
        uiBinder.bind();
        DomUtil.setVisible(clear, false);
    }


    @Override
    public AutocompleteView getAutocompleteView() {
        return new AutocompleteViewImpl(accTemplate);
    }

    @Override
    public AutocompleteMultiSelectUi getAutocompleteMultiSelectUi() {
        if (autocompleteMultiSelectUi == null)
            autocompleteMultiSelectUi = new AutocompleteMultiSelectUiImpl(autocompleteMs);
        return autocompleteMultiSelectUi;
    }
}