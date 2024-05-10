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

package com.dncomponents.client.components.table.columnclasses.treetablecell;

import com.dncomponents.client.components.core.events.close.CloseEvent;
import com.dncomponents.client.components.core.events.open.OpenEvent;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.table.ParentTableTreeCellView;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;


public class TableTreeCellParent<M> extends AbstractTableTreeCell<M> {

    public TableTreeCellParent() {
    }

    {
        setRenderer(r -> r.valuePanel.innerHTML =
                r.cell.getModel().getUserObject() == null ? "empty" :
                        r.cell.getModel().getUserObject().toString());
    }

    public TableTreeCellParent(ParentTreeCellView cellView) {
        super(cellView);
    }

    public void setExpanded(boolean b) {
        getModel().setExpanded(b);
        getOwner().drawData();
        if (b)
            OpenEvent.fire(getOwner(), getModel());
        else
            CloseEvent.fire(getOwner(), getModel());
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().addOpenCloseHandler((ClickHandler) mouseEvent
                -> setExpanded(!getModel().isExpanded()));
    }

    @Override
    public void draw() {
        super.draw();
        getCellView().setSpan(getOwner().getColumnConfigs().size());
        getCellView().setOpened(getModel().isExpanded());
    }

    @Override
    public ParentTableTreeCellView getCellView() {
        return (ParentTableTreeCellView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTreeUi().getParentTreeCellView(null);
    }


}
