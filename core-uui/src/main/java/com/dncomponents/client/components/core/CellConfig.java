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

import java.util.function.BiConsumer;
import java.util.function.Function;


public class CellConfig<T, M> extends FieldConfig<T, M> {

    protected CellFactory<T, M> cellFactory;
    protected CellRenderer<T, M> cellRenderer = r -> r.valuePanel.innerHTML = r.value == null ? "" : r.value + "";

    public CellConfig(Function<T, M> fieldGetter) {
        super(fieldGetter);
    }

    public CellConfig(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
        super(fieldGetter, fieldSetter);
    }

    public BiConsumer<T, M> getFieldSetter() {
        return fieldSetter;
    }

    public <C extends CellFactory<T, M>> C getCellFactory() {
        if (cellFactory == null) throw new RuntimeException("Cell Factory is not defined!");
        return (C) cellFactory;
    }

    public void setCellFactory(CellFactory<T, M> cellFactory) {
        this.cellFactory = cellFactory;
    }

    public void setCellRenderer(CellRenderer<T, M> cellRenderer) {
        this.cellRenderer = cellRenderer;
    }

    public CellRenderer<T, M> getCellRenderer() {
        return cellRenderer;
    }
}
