package com.dncomponents.material.client.table;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
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
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.material.client.table.cell.MdcRowDetailsCellViewImpl;
import com.dncomponents.material.client.table.cell.TableCellCheckBoxViewImpl;
import com.dncomponents.material.client.table.cell.TableCellViewImpl;
import com.dncomponents.material.client.table.footer.FooterCellViewImpl;
import com.dncomponents.material.client.table.group.ParentTableTreeCellViewImpl;
import com.dncomponents.material.client.table.header.CheckBoxHeaderTableCellViewImpl;
import com.dncomponents.material.client.table.header.HeaderTableFilterCellViewImpl;
import com.dncomponents.material.client.table.header.bar.TableBarUiImpl;
import com.dncomponents.material.client.table.header.cell.MdcHeaderTableSortCellViewImpl;
import com.dncomponents.material.client.table.header.filter.FilterPanelViewImpl;
import com.dncomponents.material.client.table.header.menu.HeaderTableMenuCellViewImpl;
import com.dncomponents.material.client.table.header.text.HeaderTableTextCellViewImpl;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class MdcTableUiImpl implements TableUi {

    @UiField("table-main")
    HTMLTemplateElement tableMain;
    @UiField("table-row")
    HTMLTemplateElement tableRow;
    @UiField("table-column")
    HTMLTemplateElement tableColumn;
    @UiField("table-checkbox-column")
    HTMLTemplateElement tableCheckBoxColumn;
    @UiField("table-tree-item-simple-parent")
    HTMLTemplateElement tableTreeItemSimpleParent;
    @UiField("table-column-sort-header")
    HTMLTemplateElement tableColumnSortHeader;
    @UiField("table-column-menu-header")
    HTMLTemplateElement tableColumnMenuHeader;
    @UiField("table-column-filter-header")
    HTMLTemplateElement tableColumnFilterHeader;
    @UiField("table-row-expander-item")
    HTMLTemplateElement tableRowExpanderItem;
    @UiField("table-header-checkbox")
    HTMLTemplateElement tableHeaderCheckbox;
    @UiField
    HTMLTemplateElement footerCell;
    @UiField("table-bar-ui")
    HTMLTemplateElement tableBarUi;
    @UiField
    HTMLTemplateElement filterPanel;


    public MdcTableUiImpl() {
        HtmlBinder.get(MdcTableUiImpl.class, this).setTemplateElement(MaterialUi.getUi().tableUi);
        HtmlBinder.get(MdcTableUiImpl.class, this).bind();
        tableView = new TableViewImpl(tableMain);
    }

    public MdcTableUiImpl(HTMLTemplateElement templateElement) {
        HtmlBinder.get(MdcTableUiImpl.class, this).setTemplateElement(templateElement);
        HtmlBinder.get(MdcTableUiImpl.class, this).bind();
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
        return new MdcRowDetailsCellViewImpl(tableRowExpanderItem);
    }

    @Override
    public HeaderTableSortCellView getHeaderTableSortCellView() {
        return new MdcHeaderTableSortCellViewImpl(tableColumnSortHeader);
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
            public BaseTreeCellView getTreeCellView() {
                return new ParentTableTreeCellViewImpl(tableTreeItemSimpleParent);
            }

            @Override
            public ParentTreeCellView getParentTreeCellView() {
                return new ParentTableTreeCellViewImpl(tableTreeItemSimpleParent);
            }

            @Override
            public TreeCellCheckboxSimpleView getTreeCellCheckBoxView() {
                return null; //we don't need this for tree group by
            }

            @Override
            public TreeCellCheckboxParentView getParentTreeCellCheckboxView() {
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
        return new TableCellCheckBoxViewImpl(tableCheckBoxColumn);
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