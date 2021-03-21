package com.dncomponents.client.components.core.events.row;

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

import java.util.function.Consumer;

public class RowAddedEvent<T> extends BaseEvent {

    private BaseCell<T, ?> row;

    private Consumer<T> updateModelConsumer;
    private Command removeRowCmd;

    public RowAddedEvent() {
        super(RowAddedHandler.TYPE);
    }

    public RowAddedEvent(BaseCell<T, ?> row) {
        this();
        this.row = row;
    }

    public BaseCell<T, ?> getRow() {
        return row;
    }

    public void updateModel(T model) {
        updateModelConsumer.accept(model);
    }

    public void removeAddedRow() {
        removeRowCmd.execute();
    }

    public static <T> void fire(IsElement source, BaseCell<T, ?> row, Consumer<T> updateModelConsumer, Command removeRowCmd) {
        fire(source.asElement(), row, updateModelConsumer, removeRowCmd);
    }

    public static <T> void fire(Element source, BaseCell<T, ?> row, Consumer<T> updateModelConsumer, Command removeRowCmd) {
        RowAddedEvent<T> event = new RowAddedEvent<>(row);
        event.updateModelConsumer = updateModelConsumer;
        event.removeRowCmd = removeRowCmd;
        source.dispatchEvent(event.asEvent());
    }


}
