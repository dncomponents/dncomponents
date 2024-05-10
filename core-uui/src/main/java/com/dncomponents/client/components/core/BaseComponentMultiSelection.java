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

package com.dncomponents.client.components.core;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.selectionmodel.AbstractMultiSelectionGroup;
import com.dncomponents.client.components.core.selectionmodel.HasEntityMultiSelectionModel;
import com.dncomponents.client.components.core.selectionmodel.MultiSelectionModel;
import com.dncomponents.client.views.core.pcg.View;

import java.util.Arrays;
import java.util.List;

public abstract class BaseComponentMultiSelection<T, V extends View, C extends HasUserValue<T> & CanSelect>
        extends BaseComponent<T, V> implements MultiSelectionModel<C>, HasEntityMultiSelectionModel<T> {


    protected AbstractMultiSelectionGroup<T, C> selectionGroup = new AbstractMultiSelectionGroup<T, C>() {
        @Override
        public void setSelectedInView(C model, boolean b) {
            model.setSelected(b);
        }
    };

    public BaseComponentMultiSelection(V ui) {
        super(ui);
    }

    @Override
    public boolean setSelected(C c, boolean b, boolean fireEvent) {
        return selectionGroup.setSelected(c, b, fireEvent);
    }

    @Override
    public boolean setSelected(C c, boolean b) {
        return setSelected(c, b, false);
    }

    @Override
    public boolean isSelected(C c) {
        return selectionGroup.isSelected(c);
    }

    @Override
    public HasValue<List<C>> getHasValue() {
        return selectionGroup.getHasValue();
    }

    @Override
    public List<C> getItems() {
        return selectionGroup.getItems();
    }

    @Override
    public List<C> getSelection() {
        return selectionGroup.getSelection();
    }

    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<C>> handler) {
        return selectionGroup.addSelectionHandler(handler);
    }

    @Override
    public void setSelected(List<C> models, boolean b, boolean fireEvent) {
        selectionGroup.setSelected(models, b, fireEvent);
    }

    public void addItem(C item) {
        selectionGroup.addItem(item);
    }

    public void removeItem(C item) {
        selectionGroup.removeItem(item);
    }

    public void removeAllItems() {
        selectionGroup.getItems().clear();
    }

    public void addItems(C... items) {
        selectionGroup.addItems(items);
    }

    public void addEntityItems(T... items) {
        Arrays.stream(items).forEach(t -> addItem(createItem(t)));
    }

    public abstract C createItem(T t);

    @Override
    public MultiSelectionModel<T> getEntitySelectionModel() {
        return selectionGroup.getEntitySelectionModel();
    }

}
