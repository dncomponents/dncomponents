package com.dncomponents.bootstrap.client.table;

import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.bootstrap.client.table.cell.RowDetailsCellViewImpl;
import com.dncomponents.bootstrap.client.table.cell.TableCellCheckBoxViewImpl;
import com.dncomponents.bootstrap.client.table.cell.TableCellViewImpl;
import com.dncomponents.bootstrap.client.table.footer.FooterCellViewImpl;
import com.dncomponents.bootstrap.client.table.group.ParentTableTreeCellViewImpl;
import com.dncomponents.bootstrap.client.table.header.CheckBoxHeaderTableCellViewImpl;
import com.dncomponents.bootstrap.client.table.header.HeaderTableFilterCellViewImpl;
import com.dncomponents.bootstrap.client.table.header.bar.TableBarUiImpl;
import com.dncomponents.bootstrap.client.table.header.filter.FilterPanelViewImpl;
import com.dncomponents.bootstrap.client.table.header.menu.HeaderTableMenuCellViewImpl;
import com.dncomponents.bootstrap.client.table.header.sort.HeaderTableSortCellViewImpl;
import com.dncomponents.bootstrap.client.table.header.text.HeaderTableTextCellViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.columnclasses.checkboxcolumn.TableCellCheckBoxView;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.dncomponents.client.views.core.pcg.cell.CellView;
import com.dncomponents.client.views.core.ui.table.TableRowView;
import com.dncomponents.client.views.core.ui.table.TableUi;
import com.dncomponents.client.views.core.ui.table.TableView;
import com.dncomponents.client.views.core.ui.table.headers.*;
import com.dncomponents.client.views.core.ui.table.headers.bar.TableBarUi;
import com.dncomponents.client.views.core.ui.tree.*;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class TableUiImpl implements TableUi {

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
    HTMLTemplateElement tableBarUi;
    @UiField
    HTMLTemplateElement filterPanel;


    public TableUiImpl() {
        HtmlBinder.get(TableUiImpl.class, this).setTemplateElement((BootstrapUi.getUi()).tableUi);
        HtmlBinder.get(TableUiImpl.class, this).bind();
        tableView = new TableViewImpl(tableMain);
    }

    public TableUiImpl(HTMLTemplateElement templateElement) {
        HtmlBinder.get(TableUiImpl.class, this).setTemplateElement(templateElement);
        HtmlBinder.get(TableUiImpl.class, this).bind();
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
    public TableBarUi getTableBarUi() {
        return new TableBarUiImpl(tableBarUi);
    }

    @Override
    public CheckBoxHeaderTableCellView getCheckBoxHeaderCellView() {
        return new CheckBoxHeaderTableCellViewImpl(tableHeaderCheckbox);
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

}