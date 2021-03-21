package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.components.RowTable;
import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

import java.util.List;

public class RowValueChangedEvent<T> extends BaseEvent {

    private RowTable<T> row;
    private List<TableCell<T, ?>> changedCells;

    public RowValueChangedEvent() {
        super(RowValueChangedHandler.TYPE);
    }

    public RowValueChangedEvent(RowTable<T> row) {
        this();
        this.row = row;
    }

    public RowTable<T> getRow() {
        return row;
    }

    public void setRow(RowTable<T> row) {
        this.row = row;
    }

    public List<TableCell<T, ?>> getChangedCells() {
        return changedCells;
    }

    public void setChangedCells(List<TableCell<T, ?>> changedCells) {
        this.changedCells = changedCells;
    }

    public void revertChanges() {
        this.row.revertValueBeforeEdit();
    }

    public static <T> void fire(IsElement source, RowTable<T> row) {
        fire(source.asElement(), row);
    }

    public static <T> void fire(Element source, RowTable<T> row) {
        RowValueChangedEvent<T> event = new RowValueChangedEvent<>(row);
        source.dispatchEvent(event.asEvent());
    }

}
