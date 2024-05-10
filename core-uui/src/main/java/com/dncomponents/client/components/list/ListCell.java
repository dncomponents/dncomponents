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

package com.dncomponents.client.components.list;

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.ListData;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.dncomponents.client.views.core.ui.list.ListUi;


public class ListCell<T, M> extends BaseCell<T, M> {

    public ListCell() {
    }

    public ListCell(BaseCellView cellView) {
        super(cellView);
    }

    @Override
    public ListData<T, M> getOwner() {
        return (ListData<T, M>) super.getOwner();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getListCellView();
    }

    @Override
    protected ListUi getUi() {
        return (ListUi) super.getUi();
    }

}
