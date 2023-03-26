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

package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.HasRowsDataList;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectView;
import elemental2.dom.Element;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.dncomponents.client.components.ListData.ListHtmlParser.getChildren;

/**
 * Created by nikolasavic
 */
public class AutocompleteMultiSelect<T> extends AbstractAutocompleteMultiSelect<T> {

    public AutocompleteMultiSelect() {
        super(Ui.get().getAutocompleteMultiSelectView(false));
    }

    public AutocompleteMultiSelect(AutocompleteMultiSelectView<T> view) {
        super(view);
    }

    public AutocompleteMultiSelect(Function<T, String> fieldGetter) {
        super(Ui.get().getAutocompleteMultiSelectView(false), fieldGetter);
    }

    public AutocompleteMultiSelect(AutocompleteMultiSelectView<T> view, Function<T, String> fieldGetter) {
        super(view, fieldGetter);
    }

    public List<T> getRowsData() {
        return view.getHasRowsData().getRowsData();
    }

    protected HasRowsDataList<T> getHasRowsDataList() {
        return (HasRowsDataList<T>) view.getHasRowsData();
    }

    public void setRowsData(List<T> rows) {
        getHasRowsDataList().setRowsData(rows);
        List<T> selection = view.getSelectionModel().getSelection();
        selection.removeIf(selected -> !rows.contains(selected));
        view.getSelectionModel().setSelected(selection, true, false);
        view.getHasRowsData().drawData();
    }

    public static class AutocompleteMultiSelectHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static AutocompleteMultiSelectHtmlParser instance;

        private AutocompleteMultiSelectHtmlParser() {
        }

        public static AutocompleteMultiSelectHtmlParser getInstance() {
            if (instance == null)
                return instance = new AutocompleteMultiSelectHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            AutocompleteMultiSelect autocomplete;
            AutocompleteMultiSelectView view = getView(AutocompleteMultiSelect.class, htmlElement, elements);
            if (view != null)
                autocomplete = new AutocompleteMultiSelect(view);
            else
                autocomplete = new AutocompleteMultiSelect();
            if (htmlElement.hasChildNodes()) {
                getChildren(autocomplete.getHasRowsDataList(), htmlElement, this);
            }
            replaceAndCopy(htmlElement, autocomplete);
            return autocomplete;
        }

        @Override
        public String getId() {
            return "dn-autocomplete-multi-select";
        }

        @Override
        public Class getClazz() {
            return AutocompleteMultiSelect.class;
        }

    }

}
