package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.components.core.events.cell.CellEditingStartedHandler;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class RowEditingStoppedEvent<T> extends BaseEvent {

    private BaseCell<T, ?> cell;
    private boolean cancel;

    public RowEditingStoppedEvent() {
        super(RowEditingStoppedHandler.TYPE);
    }

    public RowEditingStoppedEvent(BaseCell<T, ?> cell) {
        this();
        this.cell = cell;
    }

    public BaseCell<T, ?> getCell() {
        return cell;
    }

    public void setCell(BaseCell<T, ?> cell) {
        this.cell = cell;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isCancel() {
        return cancel;
    }

    public static <T> void fire(IsElement source, BaseCell<T, ?> cell) {
        fire(source.asElement(), cell);
    }

    public static <T> void fire(Element source, BaseCell<T, ?> cell) {
        RowEditingStoppedEvent<T> event = new RowEditingStoppedEvent<>(cell);
        source.dispatchEvent(event.asEvent());
    }

}
