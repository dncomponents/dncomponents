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

package com.dncomponents.client.views.core.ui.table;

import com.dncomponents.client.components.table.columnclasses.checkboxcolumn.TableCellCheckBoxView;
import com.dncomponents.client.components.table.columnclasses.editcolumn.TableCellEditView;
import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.dncomponents.client.views.core.pcg.cell.CellView;
import com.dncomponents.client.views.core.ui.table.headers.*;
import com.dncomponents.client.views.core.ui.tree.TreeUi;


public interface TableUi extends ComponentUi<TableView> {

    TableRowView getRowTableCellView();

    //table cells
    BaseCellView getTableCellView();

    BaseCellView getTableCellRowExpanderView();

    //headers
    HeaderTableSortCellView getHeaderTableSortCellView();

    HeaderTableTextCellView getHeaderTableTextCellView();

    HeaderTableMenuCellView getHeaderTableMenuCellView();
    //end headers

    //footers
    CellView getFooterCellView();
    //end footers

    CheckBoxHeaderTableCellView getCheckBoxHeaderCellView();

    TableCellEditView getTableCellEditView();

    TreeUi getGTreeGroupByUi();

    TableCellCheckBoxView getTableCellCheckBoxView();

    HeaderTableFilterCellView getHeaderTableMenuFilterView();

    FilterPanelView getFilterPanelView();

    HeaderTableEditCellView getHeaderTableEditCellView();
}
