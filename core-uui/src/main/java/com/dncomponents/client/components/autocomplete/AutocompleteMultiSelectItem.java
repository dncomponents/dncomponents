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

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectItemView;

public class AutocompleteMultiSelectItem<T> extends BaseComponent<T, AutocompleteMultiSelectItemView> {

    protected AbstractAutocompleteMultiSelect multiSelect;

    public AutocompleteMultiSelectItem(AbstractAutocompleteMultiSelect multiSelect, T value) {
        super(multiSelect.getView().getAutocompleteMultiSelectItemView());
        this.multiSelect = multiSelect;
        setRenderer(multiSelect.itemRenderer);
        this.setUserObject(value);
        init();
    }

    private void init() {
        view.addRemoveClickHandler(mouseEvent -> multiSelect.remove(AutocompleteMultiSelectItem.this));
    }

    public void setRenderer(MainRenderer<T> renderer) {
        super.setRendererBase(renderer);
    }

}
