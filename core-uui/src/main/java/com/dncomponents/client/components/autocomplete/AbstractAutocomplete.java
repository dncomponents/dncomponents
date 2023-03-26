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

import com.dncomponents.client.components.core.BaseFocusComponent;
import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.autocomplete.BaseAutocompleteView;

import java.util.List;
import java.util.function.Function;

/**
 * @author nikolasavic
 */
public class AbstractAutocomplete<T, V extends BaseAutocompleteView<T>, C> extends BaseFocusComponent<T, V> implements HasValue<C> {

    boolean listShowing;
    private C value;
    protected CellConfig<T, ?> cellConfig;
    protected final Filter<T> filter = new Filter<T>() {
        @Override
        public boolean test(T o) {
            if (view.getTextBoxCurrentValue() == null)
                return true;
            return getRowCellConfig()
                    .getFieldGetter()
                    .apply(o)
                    .toLowerCase()
                    .contains(view.getTextBoxCurrentValue().toLowerCase());
        }
    };
    protected HandlerRegistration blurRegistration;

    public AbstractAutocomplete(V view) {
        super(view);
        bind();
    }

    public AbstractAutocomplete(V view, Function<T, String> fieldGetter) {
        super(view);
        getRowCellConfig().setFieldGetter(fieldGetter);
        bind();
    }

    private boolean isMultiSelect() {
        return (this instanceof AbstractAutocompleteMultiSelect);
    }

    private void bind() {
        view.showListPanel(false, null);
        view.addSelectionHandler(event -> {
            if (!isMultiSelect())
                setValue((C) event.getSelectedItem().stream().findFirst().orElse(null), true);
        });

        view.setFilter(filter);
        view.addKeyUpHandler(event -> {
            if (event.code.equals("Escape")) {
                showList(false);
            } else if ("ArrowDown".equals(event.code)) {
                view.focusList();
            } else
                filter.fireFilterChange();
        });
        addValueChangeHandler(event -> showList(false));
        view.addButtonClickHandler(mouseEvent -> {
            showList(!listShowing);
        });
//        view.addResetButtonClickHandler(event -> {
//            setValue(null, true);
//        });
        this.blurRegistration = addBlurHandler(evt -> showList(false));
    }

    public boolean isListShowing() {
        return listShowing;
    }

    public void showList(boolean b) {
        if (!this.isEnabled()) {
            return;
        }
        view.showListPanel(b, new Command() {
            @Override
            public void execute() {
                view.setTextBoxFocused(b);
                listShowing = b;
                if (!listShowing) {
                    view.setTextBoxCurrentValue(null);
                    filter.fireFilterChange();
                }
            }
        });
    }

    @Override
    protected V getView() {
        return super.getView();
    }

    public CellConfig<T, String> getRowCellConfig() {
        return view.getRowCellConfig();
    }

    @Override
    public C getValue() {
        return value;
    }

    @Override
    public void setValue(C value) {
        this.setValue(value, false);
    }

    @Override
    public void setValue(C value, boolean fireEvents) {
        C oldValue = getValue();
        this.value = value;
        if (this.value != null) {
            if (isMultiSelect()) {
                view.getSelectionModel().setSelected((List<T>) this.value, true, false);
            } else {
                String strValue = getRowCellConfig().getFieldGetter().apply((T) this.value);
                view.setStringValue(strValue == null ? "" : strValue);
                view.getSelectionModel().setSelected((T) this.value, true, false);
            }
            view.getHasRowsData().refreshSelections();
        } else {
            view.setStringValue("");
            view.getSelectionModel().clearSelection(false);
            view.getHasRowsData().refreshSelections();
        }
        if (fireEvents) {
            C newValue = getValue();
            ValueChangeEvent.fireIfNotEqual(this, oldValue, newValue);
        }
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<C> handler) {
        return handler.addTo(asElement());
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return view.addButtonClickHandler(handler);
    }

    public void drawData() {
        view.getHasRowsData().drawData();
    }
}
