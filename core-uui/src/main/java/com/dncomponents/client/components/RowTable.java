package com.dncomponents.client.components;

import com.dncomponents.client.components.core.CellContext;
import com.dncomponents.client.components.core.CellRenderer;
import com.dncomponents.client.components.core.RendererContext;
import com.dncomponents.client.components.events.MouseCustomEvents;
import com.dncomponents.client.components.table.AbstractTableCell;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.views.core.ui.table.TableRowView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolasavic
 */
public class RowTable<T> extends AbstractTableCell<T, List> {

    ArrayList<TableCell> cells = new ArrayList<>();

    public RowTable() {
    }

    public RowTable(TableRowView view) {
        super(view);
    }

    @Override
    protected void bind() {
        new MouseCustomEvents(this.asElement());
        getCellView().addDoubleClickHandler(event -> {
            if (isPopupEditing()) {
                EditFormDialog editFormDialog = new EditFormDialog(this);
                editFormDialog.show();
            }
        });
    }


    @Override
    public void initCellValue() {
        cells.clear();
        getCellView().clearCells();
        value = new ArrayList();
        for (ColumnConfig<T, Object> columnConfig : getOwner().getColumnConfigs()) {
            Object cellValue = columnConfig.getFieldGetter().apply(getModel());
            value.add(cellValue);
            initCell(columnConfig.getCellFactory()
                    .getCell(new CellContext(columnConfig, getOwner().valueCellFactory, getModel(), getOwner())), columnConfig);
        }
    }


    public ArrayList<TableCell> getCells() {
        return cells;
    }


    public int getRow() {
        return getOwner().getCells().indexOf(this);
    }

    private void initCell(AbstractTableCell tableCell, ColumnConfig columnConfig) {
        ((TableCell) tableCell).setRowTable(this);
        getOwner().initCellListDraw(tableCell, model, columnConfig, cells, getOwner());
    }

    @Override
    public TableRowView getCellView() {
        return (TableRowView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getRowTableCellView();
    }

    @Override
    protected CellRenderer<T, List> getCellRenderer() {
        if (cellRenderer == null) {
            cellRenderer = r -> {
                RowTable<T> rowTable = (RowTable<T>) r.cell;
                for (TableCell tableCell : rowTable.getCells()) {
                    if (tableCell.getCellConfig().isVisible())
                        getCellView().addColumn(tableCell.getCellView());
                }
            };
        }
        return cellRenderer;
    }

    @Override
    public RowTable<T> initWithBuilder(BaseCellBuilder builder) {
        return super.initWithBuilder(builder);
    }

    public static class RowTableRenderer<T> implements CellRenderer<T, List> {
        private final RendererContext<T, List> r;

        public RowTableRenderer(RendererContext<T, List> r) {
            this.r = r;
        }

        @Override
        public void setValue(RendererContext<T, List> r) {
            RowTable<T> rowTable = (RowTable<T>) r.cell;
            for (TableCell tableCell : rowTable.getCells()) {
                if (tableCell.getCellConfig().isVisible())
                    ((RowTable<T>) r.cell).getCellView().addColumn(tableCell.getCellView());
            }
        }
    }

    protected RowTable(Builder<T, List> builder) {
        super(builder);
    }

    public static class Builder<T, M> extends AbstractTableCell.Builder<T, M> {
        @Override
        public RowTable build() {
            return new RowTable(this);
        }
    }

    @Override
    public boolean isEditable() {
        return super.isEditable() && !isPopupEditing();
    }
}
