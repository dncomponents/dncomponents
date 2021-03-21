package com.dncomponents.client.components.core.events.cell;

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class CellValueChangedEvent<T> extends BaseEvent {

    private BaseCell<T, ?> cell;
    private Object oldValue;
    private Object newValue;

    public CellValueChangedEvent() {
        super(CellValueChangedHandler.TYPE);
    }

    public CellValueChangedEvent(BaseCell<T, ?> cell) {
        this();
        this.cell = cell;
    }

    public BaseCell<T, ?> getCell() {
        return cell;
    }

    public void setCell(BaseCell<T, ?> cell) {
        this.cell = cell;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public void revertChanges() {
        this.cell.revertValueBeforeEdit();
    }

    public static <T> void fire(IsElement source, BaseCell<T, ?> cell) {
        fire(source.asElement(), cell);
    }

    public static <T> void fire(Element source, BaseCell<T, ?> cell) {
        CellValueChangedEvent<T> event = new CellValueChangedEvent<>(cell);
        source.dispatchEvent(event.asEvent());
    }
}
