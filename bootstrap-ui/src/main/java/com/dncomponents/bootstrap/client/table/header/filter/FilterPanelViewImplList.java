/*
 * Copyright 2023 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.bootstrap.client.table.header.filter;

import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.autocomplete.list.AutocompleteViewImpl;
import com.dncomponents.bootstrap.client.autocomplete.multiselect.AutocompleteListOrTreeMultiSelectViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteView;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectView;
import com.dncomponents.client.views.core.ui.table.headers.FilterPanelListView;
import elemental2.dom.HTMLTemplateElement;

public class FilterPanelViewImplList<T> extends FilterPanelViewImpl<T> implements FilterPanelListView<T> {

    private AutocompleteMultiSelectView autocompleteMultiSelectUi;


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
    public AutocompleteMultiSelectView getAutocompleteMultiSelectUi() {
        if (autocompleteMultiSelectUi == null)
            autocompleteMultiSelectUi = AutocompleteListOrTreeMultiSelectViewImpl.getInstance(autocompleteMs, false);
        return autocompleteMultiSelectUi;
    }
}
