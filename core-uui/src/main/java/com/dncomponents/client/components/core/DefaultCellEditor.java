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

import com.dncomponents.client.components.HasSelectionModel;
import com.dncomponents.client.components.checkbox.HasIsElement;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.focus.Focusable;
import com.dncomponents.client.components.core.events.focus.HasBlurHandlers;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.selectionmodel.HasEntityMultiSelectionModel;
import com.dncomponents.client.components.core.selectionmodel.HasEntitySingleSelectionModel;
import com.dncomponents.client.views.IsElement;


public class DefaultCellEditor<M> implements CellEditor<M> {

    protected Object c;

    protected Command endEditing;

    public DefaultCellEditor(Object c) {
        this.c = c;
    }

    @Override
    public HasValue<M> getHasValue() {
        if (c instanceof HasEntitySingleSelectionModel)
            return ((HasEntitySingleSelectionModel) c).getEntitySelectionModel().getHasValue();
        else if (c instanceof HasEntityMultiSelectionModel)
            return ((HasEntityMultiSelectionModel) c).getEntitySelectionModel().getHasValue();
        else if (c instanceof HasSelectionModel)
            return ((HasSelectionModel) c).getSelectionModel().getHasValue();
        else
            return (HasValue<M>) c;
    }

    @Override
    public Focusable getFocusable() {
        return (Focusable) c;
    }

    @Override
    public IsElement getIsElement() {
        if (c instanceof HasIsElement)
            return ((HasIsElement) c).asIsElement();
        else
            return (IsElement) c;
    }

    @Override
    public void startEditing() {

    }

    @Override
    public HasBlurHandlers getHasBlurHandler() {
        return (HasBlurHandlers) c;
    }

    @Override
    public void setEndEditingHandler(Command endEditing) {
        this.endEditing = endEditing;
    }

}
