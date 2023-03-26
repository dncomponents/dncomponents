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

package com.dncomponents.client.components.core.selectionmodel;

import com.dncomponents.client.components.core.HasUserValue;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.selectionmodel.helper.AbstractSelectionHandler;
import com.dncomponents.client.components.core.selectionmodel.helper.AbstractValueChangeHandler;
import elemental2.dom.CustomEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AbstractMultiSelectionGroup<T, C extends HasUserValue<T>>
        extends DefaultMultiSelectionModel<C> implements HasEntityMultiSelectionModel<T> {

    List<C> possibleValues = new ArrayList<>();

    public void removeItem(C value) {
        possibleValues.remove(value);
        selection.remove(value);
    }

    public void addItems(C... value) {
        Arrays.stream(value).forEach(e -> addItem(e));
    }

    public void addItem(C value) {
        if (value != null && !possibleValues.contains(value))
            possibleValues.add(value);
    }

    public void addItems(List<? extends C> values) {
        values.forEach((Consumer<C>) this::addItem);
    }

    @Override
    public List<C> getItems() {
        return possibleValues;
    }

    public C getElementByModel(T value) {
        return getItems()
                .stream()
                .filter(value1 -> value1.getUserObject() == value)
                .findFirst()
                .orElse(null);
    }

    @Override
    protected void fireSelectionChange() {
        super.fireSelectionChange();
        SelectionEvent.fire(entitySelectionModel, entitySelectionModel.getSelection());
        ValueChangeEvent.fire(entitySelectionModel.getHasValue(),
                selection.stream().map(c -> c.getUserObject()).collect(Collectors.toList()),entitySelectionModel.getSelection());
    }

    @Override
    public void fireEvent(CustomEvent event) {
        ensureHandlers().dispatchEvent(event);
    }

    class EntitySelectionModel extends AbstractSelectionHandler<List<T>> implements MultiSelectionModel<T> {

        private HasValue<List<T>> entityHasValue = new HasValueModel();

        @Override
        public List<T> getSelection() {
            return AbstractMultiSelectionGroup.this.selection.stream().map(HasUserValue::getUserObject).collect(Collectors.toList());
        }

        @Override
        public HasValue<List<T>> getHasValue() {
            return entityHasValue;
        }

        @Override
        public boolean setSelected(T model, boolean b, boolean fireEvent) {
            return AbstractMultiSelectionGroup.this.setSelected(getElementByModel(model), b, fireEvent);
        }

        @Override
        public List<T> getItems() {
            return AbstractMultiSelectionGroup.this.possibleValues.stream().map(HasUserValue::getUserObject).collect(Collectors.toList());
        }

        @Override
        public void setSelected(List<T> models, boolean b, boolean fireEvent) {
            AbstractMultiSelectionGroup.this.setSelected(models.stream().map(t -> getElementByModel(t)).collect(Collectors.toList()), true, fireEvent);
        }

        class HasValueModel extends AbstractValueChangeHandler<List<T>> {

            @Override
            public List<T> getValue() {
                return getSelection();
            }

            @Override
            public void setValue(List<T> value, boolean fireEvents) {
                setSelected(value, true, fireEvents);
            }

        }
    }

    private MultiSelectionModel<T> entitySelectionModel = new EntitySelectionModel();

    @Override
    public MultiSelectionModel<T> getEntitySelectionModel() {
        return entitySelectionModel;
    }

}
