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
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.pcg.cell.CellView;
import elemental2.dom.HTMLElement;


public abstract class AbstractCell<P, M, CW extends CellView> implements IsElement {

    protected P model;
    protected M value;

    protected AbstractCellComponent owner;

    protected CellConfig<P, M> cellConfig;

    protected CW cellView;

    public AbstractCell() {
    }

    public AbstractCell(CW cellView) {
        this.cellView = cellView;
    }

    public P getModel() {
        return model;
    }

    protected void setModel(P model) {
        this.model = model;
    }

    public M getValue() {
        return value;
    }

    public AbstractCellComponent getOwner() {
        return owner;
    }

    protected void setOwner(AbstractCellComponent owner) {
        this.owner = owner;
        if (cellView == null)
            initViewFromOwner();
    }

    protected void initViewFromOwner() {
    }

    protected CW getCellView() {
        return cellView;
    }


    public abstract void draw();

    /**
     * this method is called after all cell fields are initialised by the owner widget
     */
    protected void bind() {
    }

    void removeFromParent() {
        cellView.asElement().remove();
    }

    @Override
    public HTMLElement asElement() {
        return cellView.asElement();
    }

    protected ComponentUi getUi() {
        return getOwner().getView();
    }

    protected static ComponentUi getUi(AbstractCellComponent component) {
        return component.getView();
    }

    public void setCellConfig(CellConfig<P, M> cellConfig) {
        this.cellConfig = cellConfig;
    }

    public CellConfig<P, M> getCellConfig() {
        return cellConfig;
    }
}
