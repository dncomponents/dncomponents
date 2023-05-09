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

package com.dncomponents.bootstrap.client.table;

import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.bootstrap.client.table.cell.RowDetailsCellViewImpl;
import com.dncomponents.bootstrap.client.table.cell.TableCellCheckBoxViewImpl;
import com.dncomponents.bootstrap.client.table.cell.TableCellEditViewImpl;
import com.dncomponents.bootstrap.client.table.cell.TableCellViewImpl;
import com.dncomponents.bootstrap.client.table.footer.FooterCellViewImpl;
import com.dncomponents.bootstrap.client.table.group.ParentTableTreeCellViewImpl;
import com.dncomponents.bootstrap.client.table.header.CheckBoxHeaderTableCellViewImpl;
import com.dncomponents.bootstrap.client.table.header.HeaderTableEditCellViewImpl;
import com.dncomponents.bootstrap.client.table.header.HeaderTableFilterCellViewImpl;
import com.dncomponents.bootstrap.client.table.header.filter.FilterPanelViewImpl;
import com.dncomponents.bootstrap.client.table.header.menu.HeaderTableMenuCellViewImpl;
import com.dncomponents.bootstrap.client.table.header.sort.HeaderTableSortCellViewImpl;
import com.dncomponents.bootstrap.client.table.header.text.HeaderTableTextCellViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.columnclasses.checkboxcolumn.TableCellCheckBoxView;
import com.dncomponents.client.components.table.columnclasses.editcolumn.TableCellEditView;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.dncomponents.client.views.core.pcg.cell.CellView;
import com.dncomponents.client.views.core.ui.table.TableRowView;
import com.dncomponents.client.views.core.ui.table.TableUi;
import com.dncomponents.client.views.core.ui.table.TableView;
import com.dncomponents.client.views.core.ui.table.headers.*;
import com.dncomponents.client.views.core.ui.tree.*;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class TableUiImpl implements TableUi {
    HtmlBinder binder = HtmlBinder.create(TableUiImpl.class, this);

    public static final String VIEW_ID = "default";
    @UiField
    HTMLTemplateElement tableMain;
    @UiField
    HTMLTemplateElement tableRow;
    @UiField
    HTMLTemplateElement tableColumn;
    @UiField
    HTMLTemplateElement tableCheckboxColumn;
    @UiField
    HTMLTemplateElement tableTreeItemSimpleParent;
    @UiField
    HTMLTemplateElement tableColumnSortHeader;
    @UiField
    HTMLTemplateElement tableColumnMenuHeader;
    @UiField
    HTMLTemplateElement tableColumnFilterHeader;
    @UiField
    HTMLTemplateElement tableRowExpanderItem;
    @UiField
    HTMLTemplateElement tableHeaderCheckbox;
    @UiField
    HTMLTemplateElement footerCell;
    @UiField
    HTMLTemplateElement filterPanel;
    @UiField
    HTMLTemplateElement tableCellEdit;
    @UiField
    HTMLTemplateElement headerEdit;


    public TableUiImpl() {
        binder.setTemplateElement((BootstrapUi.getUi()).tableUi);
        binder.bind();
        tableView = new TableViewImpl(tableMain);
    }

    public TableUiImpl(HTMLTemplateElement templateElement) {
        binder.setTemplateElement(templateElement);
        binder.bind();
        tableView = new TableViewImpl(tableMain);
    }


    TableView tableView;

    @Override
    public TableRowView getRowTableCellView() {
        return new TableRowViewImpl(tableRow);
    }

    @Override
    public BaseCellView getTableCellView() {
        return new TableCellViewImpl(tableColumn);
    }

    @Override
    public BaseCellView getTableCellRowExpanderView() {
        return new RowDetailsCellViewImpl(tableRowExpanderItem);
    }

    @Override
    public HeaderTableSortCellView getHeaderTableSortCellView() {
        return new HeaderTableSortCellViewImpl(tableColumnSortHeader);
    }

    @Override
    public HeaderTableTextCellView getHeaderTableTextCellView() {
        return new HeaderTableTextCellViewImpl(tableColumn);
    }

    @Override
    public HeaderTableMenuCellView getHeaderTableMenuCellView() {
        return new HeaderTableMenuCellViewImpl(tableColumnMenuHeader);
    }

    @Override
    public CellView getFooterCellView() {
        return new FooterCellViewImpl(footerCell);
    }


    @Override
    public CheckBoxHeaderTableCellView getCheckBoxHeaderCellView() {
        return new CheckBoxHeaderTableCellViewImpl(tableHeaderCheckbox);
    }

    @Override
    public TableCellEditView getTableCellEditView() {
        return new TableCellEditViewImpl(tableCellEdit);
    }

    @Override
    public TableView getRootView() {
        return tableView;
    }

    @Override
    public TreeUi getGTreeGroupByUi() {
        return new TreeUi() {
            @Override
            public BaseTreeCellView getTreeCellView(String icon) {
                return new ParentTableTreeCellViewImpl(tableTreeItemSimpleParent);
            }

            @Override
            public ParentTreeCellView getParentTreeCellView(String icon) {
                return new ParentTableTreeCellViewImpl(tableTreeItemSimpleParent);
            }

            @Override
            public TreeCellCheckboxSimpleView getTreeCellCheckBoxView(String icon) {
                return null; //we don't need this for tree group by
            }

            @Override
            public TreeCellCheckboxParentView getParentTreeCellCheckboxView(String icon) {
                return null; //we don't need this for tree group by
            }

            @Override
            public TreeView getRootView() {
                return tableView;
            }
        };
    }

    @Override
    public TableCellCheckBoxView getTableCellCheckBoxView() {
        return new TableCellCheckBoxViewImpl(tableCheckboxColumn);
    }

    @Override
    public HeaderTableFilterCellView getHeaderTableMenuFilterView() {
        return new HeaderTableFilterCellViewImpl(tableColumnFilterHeader);
    }

    @Override
    public FilterPanelView getFilterPanelView() {
        return new FilterPanelViewImpl(filterPanel);
    }

    @Override
    public HeaderTableEditCellView getHeaderTableEditCellView() {
        return new HeaderTableEditCellViewImpl(headerEdit);
    }
}
