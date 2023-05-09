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

package com.dncomponents.bootstrap.client.autocomplete.list;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.bootstrap.client.autocomplete.BaseAutocompleteViewImpl;
import com.dncomponents.client.components.HasRowsDataList;
import com.dncomponents.client.components.ListCellConfig;
import com.dncomponents.client.components.ListData;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

import java.util.List;
import java.util.function.Function;

/**
 * @author nikolasavic
 */

@Component
public class AutocompleteViewImpl<T> extends BaseAutocompleteViewImpl<T> implements AutocompleteView<T> {

    protected static final String VIEW_ID = "DEFAULT";
    @UiField
    ListData<T, String> list;

    HtmlBinder uiBinder = HtmlBinder.create(AutocompleteViewImpl.class, this);

    public AutocompleteViewImpl() {
        uiBinder.bind();
        init();
    }

    public AutocompleteViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }

    public AutocompleteViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    private void init() {
        list.setEditable(false);
        list.getRowCellConfig().setFieldGetter(t -> t + "");
        list.getSelectionModel().setSelectionMode(DefaultMultiSelectionModel.SelectionMode.SINGLE);
    }

    @Override
    public void focusList() {
        if (!list.getCells().isEmpty())
            list.getSelectionModel().focusCell(list.getRowCell(0));
    }

    @Override
    public HTMLElement getFocusElement() {
        return textBox.asElement();
    }

    @Override
    public DefaultMultiSelectionModel getSelectionModel() {
        return list.getSelectionModel();
    }

    @Override
    public void setFilter(Filter<T> filter) {
        list.addFilter(filter);
    }

    @Override
    public HasRowsDataList<T> getHasRowsData() {
        return list;
    }

    @Override
    public void setFieldGetter(Function<T, String> fieldGetter) {
        list.getRowCellConfig().setFieldGetter(fieldGetter);
    }

    @Override
    public ListCellConfig<T, String> getRowCellConfig() {
        return list.getRowCellConfig();
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<T>> handler) {
        return list.getSelectionModel().addSelectionHandler(handler);
    }
}
