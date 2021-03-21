package com.dncomponents.client.views.core.ui.table;

import com.dncomponents.client.components.table.columnclasses.checkboxcolumn.TableCellCheckBoxView;
import com.dncomponents.client.components.table.columnclasses.editcolumn.TableCellEditView;
import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.dncomponents.client.views.core.pcg.cell.CellView;
import com.dncomponents.client.views.core.ui.table.headers.*;
import com.dncomponents.client.views.core.ui.table.headers.bar.TableBarUi;
import com.dncomponents.client.views.core.ui.tree.TreeUi;

/**
 * @author nikolasavic
 */
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

    //bar
    TableBarUi getTableBarUi();

    CheckBoxHeaderTableCellView getCheckBoxHeaderCellView();

    TableCellEditView getTableCellEditView();

    TreeUi getGTreeGroupByUi();

    TableCellCheckBoxView getTableCellCheckBoxView();

    HeaderTableFilterCellView getHeaderTableMenuFilterView();

    FilterPanelView getFilterPanelView();

    HeaderTableEditCellView getHeaderTableEditCellView();
}
