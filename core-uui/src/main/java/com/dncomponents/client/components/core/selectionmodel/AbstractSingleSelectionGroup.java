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

package com.dncomponents.client.components.core.selectionmodel;

import com.dncomponents.client.components.core.HasUserValue;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.components.core.events.value.AbstractValueChangeHandler;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AbstractSingleSelectionGroup<T, C extends HasUserValue<T>>
        extends DefaultSingleSelectionModel<C> implements HasEntitySingleSelectionModel<T> {

    List<C> possibleValues = new ArrayList<>();

    public void removeItem(C value) {
        possibleValues.remove(value);
        if (value.equals(selection))
            selection = null;
    }

    public void addItems(C... value) {
        Arrays.stream(value).forEach(e -> addItem(e));
    }

    public void addItem(C value) {
        if (value != null && !possibleValues.contains(value))
            possibleValues.add(value);
        else throw new IllegalArgumentException("Can't add " + value + " as item");
    }

    public void addItems(List<? extends C> possibleValues) {
        possibleValues.forEach((Consumer<C>) this::addItem);
    }

    @Override
    public List<C> getItems() {
        return possibleValues;
    }

    public C getElementByModel(T value) {
        return possibleValues
                .stream()
                .filter(value1 -> value1.getUserObject() == value)
                .findFirst()
                .orElse(null);
    }

    @Override
    protected void fireSelectionChange() {
        super.fireSelectionChange();
        SelectionEvent.fire(entitySelectionModel, entitySelectionModel.getSelection());
        ValueChangeEvent.fire(entitySelectionModel.getHasValue(), selection.getUserObject());
    }

    private class EntitySingleSelectionModel extends AbstractSelectionHandler<T> implements SingleSelectionModel<T> {
        private HasValue<T> entityHasValue = new HasValueModel();

        @Override
        public T getSelection() {
            return AbstractSingleSelectionGroup.this.selection == null ? null : AbstractSingleSelectionGroup.this.selection.getUserObject();
        }

        @Override
        public HasValue<T> getHasValue() {
            return entityHasValue;
        }

        @Override
        public boolean setSelected(T model, boolean b, boolean fireEvent) {
            return AbstractSingleSelectionGroup.this.setSelected(getElementByModel(model), b, fireEvent);
        }

        @Override
        public List<T> getItems() {
            return possibleValues.stream().map(HasUserValue::getUserObject).collect(Collectors.toList());
        }

        private class HasValueModel extends AbstractValueChangeHandler<T> {

            @Override
            public T getValue() {
                return getSelection();
            }

            @Override
            public void setValue(T value, boolean fireEvents) {
                setSelected(value, true, fireEvents);
            }
        }
    }


    private SingleSelectionModel<T> entitySelectionModel = new EntitySingleSelectionModel();

    @Override
    public SingleSelectionModel<T> getEntitySelectionModel() {
        return entitySelectionModel;
    }

}
