/*
 * Copyright 2024 dncomponents
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

import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.MainRendererImpl;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


public abstract class AbstractAutocompleteMultiSelect<T> extends AbstractAutocomplete<T, AutocompleteMultiSelectView<T>, List<T>> {

    protected MainRenderer<T> itemRenderer = new MainRendererImpl<>();

    protected List<AutocompleteMultiSelectItem<T>> items = new ArrayList<>();
    private Consumer<Consumer<Boolean>> removeItemConsumer;

    public void setItemRenderer(MainRenderer<T> itemRenderer) {
        this.itemRenderer = itemRenderer;
    }


    public void onItemRemoved(Consumer<Consumer<Boolean>> removeItemConsumer) {
        this.removeItemConsumer = removeItemConsumer;
    }


    void remove(AutocompleteMultiSelectItem item) {
        view.getSelectionModel().setSelected((T) item.getUserObject(), false, true);
        view.getHasRowsData().refreshSelections();
    }

    public AbstractAutocompleteMultiSelect(AutocompleteMultiSelectView<T> view) {
        super(view);
        bind();
    }

    public AbstractAutocompleteMultiSelect(AutocompleteMultiSelectView<T> view, Function<T, String> fieldGetter) {
        super(view, fieldGetter);
        bind();
    }

    private void bind() {
//        addValueChangeHandler(event -> showList(false));
        view.getSelectionModel().getHasValue().addValueChangeHandler(event -> {
            if (event.getOldValue().size() > event.getValue().size()) {
                List<T> oldValue = event.getOldValue();
                if (removeItemConsumer != null) {
                    removeItemConsumer.accept(ok -> {
                        if (ok) {
                            setValue(event.getValue(), true);
                        } else {
                            view.getSelectionModel().setSelected(oldValue, true, false);
                            view.getHasRowsData().refreshSelections();
                        }
                    });
                } else {
                    setValue(event.getValue(), true);
                }
            } else {
                setValue(event.getValue(), true);
            }
        });
    }

    public void setHideListOnValueChanged(boolean hideListOnValueChanged) {
        this.hideListOnValueChanged = hideListOnValueChanged;
    }

    public boolean isHideListOnValueChanged() {
        return hideListOnValueChanged;
    }

    @Override
    public void setValue(List<T> value, boolean fireEvents) {
        super.setValue(value, fireEvents);
        changed(value);
    }

    protected void changed(List<T> value) {
        view.clearItems();
        if (value != null) {
            for (T t : value) {
                AutocompleteMultiSelectItem<T> item = new AutocompleteMultiSelectItem<T>(AbstractAutocompleteMultiSelect.this, t);
                view.addItem(item);
            }
        }
    }

    @Override
    protected AutocompleteMultiSelectView<T> getView() {
        return super.getView();
    }

}
