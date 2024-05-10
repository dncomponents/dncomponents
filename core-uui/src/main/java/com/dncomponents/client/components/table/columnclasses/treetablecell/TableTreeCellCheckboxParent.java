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

import com.dncomponents.client.components.tree.TreeMultiSelectionModel;
import com.dncomponents.client.views.core.ui.table.ParentTableTreeCheckboxCellView;
import com.dncomponents.client.views.core.ui.tree.TreeCellCheckboxParentView;


public class TableTreeCellCheckboxParent<M> extends TableTreeCellParent<M> {

    public TableTreeCellCheckboxParent(TreeCellCheckboxParentView cellView) {
        super(cellView);
    }

    public TableTreeCellCheckboxParent() {
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().getCheckBox().addValueChangeHandler(event ->
                getOwner().getSelectionModel().setSelected(getModel(), event.getValue(), true));
        getOwner().getSelectionModel().addSelectionHandler(event -> draw());
    }

    @Override
    protected void setSelection() {
        TreeMultiSelectionModel.SelectionState state = getSelectionModel().getNodeState(getModel());
        if (state == TreeMultiSelectionModel.SelectionState.INDETERMINATE) {
            getCellView().setIndeterminate(true);
        } else {
            getCellView().setIndeterminate(false);
            setSelected(state == TreeMultiSelectionModel.SelectionState.CHECKED);
        }
    }

    @Override
    public void draw() {
        super.draw();
        setSelection();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTreeUi().getParentTreeCellCheckboxView(null);
    }

    TreeMultiSelectionModel getSelectionModel() {
        return (TreeMultiSelectionModel) getOwner().getSelectionModel();
    }

    @Override
    public ParentTableTreeCheckboxCellView getCellView() {
        return (ParentTableTreeCheckboxCellView) super.getCellView();
    }
}
