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

package com.dncomponents.client.components;

import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.list.ListCellFactory;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ListCellConfig<T, M> extends CellConfig<T, M> {

    public ListCellConfig() {
        super(t -> null);
        setCellFactory(c -> c.createDefaultCell());
    }

    public ListCellConfig(Function<T, M> fieldGetter, BiConsumer<T, M> fieldSetter) {
        super(fieldGetter, fieldSetter);
    }

    public void setCellFactory(ListCellFactory<T, M> cellFactory) {
        super.setCellFactory(cellFactory);
    }

    @Override
    public ListCellFactory<T, M> getCellFactory() {
        return super.getCellFactory();
    }

}
