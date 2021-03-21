package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

public class RowRemovedEvent<T> extends BaseEvent {

    private BaseCell<T, ?> row;
    private Command revertDeletedCmd;

    public RowRemovedEvent() {
        super(RowRemovedHandler.TYPE);
    }

    public RowRemovedEvent(BaseCell<T, ?> row) {
        this();
        this.row = row;
    }

    public BaseCell<T, ?> getRow() {
        return row;
    }

    public void setRow(BaseCell<T, ?> row) {
        this.row = row;
    }

    public void revertDeletedRow() {
        if (this.revertDeletedCmd != null)
            this.revertDeletedCmd.execute();
    }

    public static <T> void fire(IsElement source, BaseCell<T, ?> row, Command cmd) {
        fire(source.asElement(), row, cmd);
    }


    public static <T> void fire(Element source, BaseCell<T, ?> row, Command cmd) {
        RowRemovedEvent<T> event = new RowRemovedEvent<>(row);
        event.revertDeletedCmd = cmd;
        source.dispatchEvent(event.asEvent());
    }

}
