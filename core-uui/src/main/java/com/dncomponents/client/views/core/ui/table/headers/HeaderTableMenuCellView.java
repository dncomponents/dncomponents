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

package com.dncomponents.client.views.core.ui.table.headers;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.table.header.HeaderFiltering;
import com.dncomponents.client.components.table.header.SortingDirection;
import com.dncomponents.client.components.table.header.filter.FilterValueHandler;


public interface HeaderTableMenuCellView extends HeaderTableSortCellView {

    void setColumn(ColumnConfig column);

    void setGroupedBy(SortingDirection direction);

    void setFiltered(HeaderFiltering b);

    void setPresenter(Presenter presenter);

    interface Presenter extends SortPresenter, FilterValueHandler {
        void groupBy(SortingDirection direction);
    }

}
