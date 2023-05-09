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

package com.dncomponents.bootstrap.client.autocomplete.multiselect;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectItemView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class AutocompleteMultiSelectItemViewImpl implements AutocompleteMultiSelectItemView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement mainPanel;
    @UiField
    HTMLElement removeBtn;

    HtmlBinder uiBinder = HtmlBinder.create(AutocompleteMultiSelectItemViewImpl.class, this);

    public AutocompleteMultiSelectItemViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public AutocompleteMultiSelectItemViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void addRemoveClickHandler(ClickHandler clickHandler) {
        clickHandler.addTo(removeBtn);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public MainViewSlots getViewSlots() {
        return () -> mainPanel;
    }
}
