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

import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.tree.TreeCellSimple;
import com.dncomponents.client.views.core.ui.tree.TreeCellCheckboxSimpleView;


public class TreeCellCheckboxSimple<T, M> extends TreeCellSimple<T, M> {

    public TreeCellCheckboxSimple() {
    }

    public TreeCellCheckboxSimple(TreeCellCheckboxSimpleView treeCellView) {
        super(treeCellView);
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().getCheckBox().addValueChangeHandler((ValueChangeEvent<Boolean> event) ->
                getOwner().getSelectionModel().setSelected(getModel(), event.getValue(), true)
        );
//        getOwner().getSelectionModel().setNavigator(false);
    }

    @Override
    public TreeCellCheckboxSimpleView getCellView() {
        return (TreeCellCheckboxSimpleView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTreeCellCheckBoxView(icon);
    }

}
