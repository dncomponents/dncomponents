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

package com.dncomponents.client.components.core;

import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.components.BaseCell;

/**
 * @author nikolasavic
 */
public class CellContext<T, M> {

    public CellContext(CellConfig<T, M> cellConfig, CellFactory<T, M> defaultCellFactory, T model, AbstractCellComponent owner) {
        this.defaultCellFactory = defaultCellFactory;
        this.cellConfig = cellConfig;
        this.model = model;
        this.owner = owner;
    }

    CellConfig<T, M> cellConfig;
    public CellFactory<T, M> defaultCellFactory;
    public T model;
    public AbstractCellComponent owner;

    public M value() {
        return cellConfig.getFieldGetter().apply(model);
    }

    public <B extends BaseCell<T, M>> B createDefaultCell() {
        return (B) defaultCellFactory.getCell(this);
    }
}
