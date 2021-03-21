package com.dncomponents.client.components;

import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.CellContext;
import com.dncomponents.client.components.core.CellRenderer;
import com.dncomponents.client.components.core.RendererContext;
import com.dncomponents.client.components.core.events.form.ModelChangedEvent;
import com.dncomponents.client.components.core.events.hide.HideEvent;
import com.dncomponents.client.components.core.events.hide.HideHandler;
import com.dncomponents.client.components.core.events.row.RowEditingStartedEvent;
import com.dncomponents.client.components.core.events.row.RowEditingStoppedEvent;
import com.dncomponents.client.components.core.events.row.RowValueChangedEvent;
import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.components.events.MouseCustomEvents;
import com.dncomponents.client.components.form.Form;
import com.dncomponents.client.components.modal.Dialog;
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
    ArrayList<TableCell<T, ?>> changedCells = new ArrayList<>();

    boolean temp = false;

    public boolean fromRow = false;

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
                Form form = new Form();
                for (TableCell cell : this.cells)
                    form.addFormConfigs(cell.cellConfig);
                form.setFormData(this.getModel());
                form.drawData();
                Dialog dialog = new Dialog();
                dialog.setContent(se -> se.appendChild(form.asElement()));
                dialog.setFooter(e -> e.appendChild(new Button("Save", ev -> {
                    if (form.save()) {
                        ModelChangedEvent.fire(getOwner(), getModel());
                        this.draw();
                        dialog.hide();
                    }
                }).asElement()));
                form.showSubmitButton(false);
                dialog.show();
            }
        });
    }

    public void startEditing() {
        if (getOwner().getCurrentRowEdited() != null &&
                !getOwner().getCurrentRowEdited().equals(this))
            getOwner().stopCurrentRowEdited();
        TableCell firstCell = null;
        for (TableCell tableCell : cells) {
            if (tableCell.isEditable() && firstCell == null)
                firstCell = tableCell;
            tableCell.ensureEditor().fromRow = fromRow;
            tableCell.startEditing();
        }
        firstCell.cellEditing.focus();
        getOwner().setCurrentRowEdited(this);
        getOwner().fireEvent(new RowEditingStartedEvent<>(this));
    }

    public void stopEditing() {
        if (temp) {
            this.asElement().remove();
        } else {
            cells.forEach(tableCell -> tableCell.stopEditing());
        }
        getOwner().fireEvent(new RowEditingStoppedEvent<>(this));
    }

    public boolean isEditing() {
        return getOwner().getCurrentRowEdited() == this;
    }

    public void revertValueBeforeEdit() {
        for (TableCell<T, ?> changedCell : changedCells) {
            changedCell.revertValueBeforeEdit();
        }
    }

    public void delete() {
        getOwner().removeRow(getModel());
    }

    public boolean areCellsValid() {
        boolean valid = true;
        for (TableCell cell : cells) {
            try {
                if (cell.cellEditing != null) {
                    cell.cellEditing.validateEnteredValue();
                }
            } catch (ValidationException e) {
                valid = false;
            }
        }
        return valid;
    }

    public boolean saveChanges() {
        if (areCellsValid()) {
            changedCells.clear();
            for (TableCell cell : cells) {
                if (cell.cellEditing != null && cell.cellEditing.hasChanges()) {
                    changedCells.add(cell);
                    cell.cellEditing.saveChanges();
                } else {
                    cell.stopEditing();
                }
            }
            if (temp) {
                if (hasChangedCells())
                    getOwner().insertRow(getModel(), 0);
                this.asElement().remove();
                getOwner().drawData();
            } else {
                ModelChangedEvent.fire(getOwner(), getModel());
                final RowValueChangedEvent<T> rowValueChangedEvent = new RowValueChangedEvent<>(this);
                rowValueChangedEvent.setChangedCells(changedCells);
                getOwner().fireEvent(rowValueChangedEvent);
                return true;
            }
        }
        return false;
    }

    public boolean hasChangedCells() {
        return !changedCells.isEmpty();
    }

    @Override
    protected CellEditing ensureEditor() {
        final CellEditing<T, List> editor = super.ensureEditor();
        return editor;
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

    @Override
    public boolean isEditable() {
        return super.isEditable() && !isPopupEditing();
    }
}
