package com.dncomponents.client.components.core.events.cell;

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class CellEditingStartedEvent<T> extends BaseEvent {

    private BaseCell<T, ?> cell;
    private boolean cancel;

    public CellEditingStartedEvent() {
        super(CellEditingStartedHandler.TYPE);
    }

    public CellEditingStartedEvent(BaseCell<T, ?> cell) {
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
        CellEditingStartedEvent<T> event = new CellEditingStartedEvent<>(cell);
        source.dispatchEvent(event.asEvent());
    }

}
