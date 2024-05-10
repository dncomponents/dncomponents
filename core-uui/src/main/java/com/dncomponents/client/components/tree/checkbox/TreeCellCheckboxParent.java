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

package com.dncomponents.client.components.tree.checkbox;

import com.dncomponents.client.components.tree.TreeCellParent;
import com.dncomponents.client.components.tree.TreeMultiSelectionModel;
import com.dncomponents.client.views.core.ui.tree.TreeCellCheckboxParentView;


public class TreeCellCheckboxParent<T, M> extends TreeCellParent<T, M> {

    public TreeCellCheckboxParent(TreeCellCheckboxParentView cellView) {
        super(cellView);
    }

    public TreeCellCheckboxParent() {
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().getCheckBox().addValueChangeHandler(event ->
                getOwner().getSelectionModel().setSelected(getModel(), event.getValue(), true));
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
    protected void initViewFromOwner() {
        cellView = getUi().getParentTreeCellCheckboxView(icon);
    }

    TreeMultiSelectionModel getSelectionModel() {
        return (TreeMultiSelectionModel) getOwner().getSelectionModel();
    }

    @Override
    public TreeCellCheckboxParentView getCellView() {
        return (TreeCellCheckboxParentView) super.getCellView();
    }

}
