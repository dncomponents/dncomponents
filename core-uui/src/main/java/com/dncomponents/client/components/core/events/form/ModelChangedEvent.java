package com.dncomponents.client.components.core.events.form;

import com.dncomponents.client.components.core.events.BaseEvent;
import com.dncomponents.client.views.IsElement;

public class ModelChangedEvent<T> extends BaseEvent {

    private T model;

    public ModelChangedEvent() {
        super(ModelChangedHandler.TYPE);
    }

    public ModelChangedEvent(T model) {
        this();
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public static <T> void fire(IsElement source, T model) {
        ModelChangedEvent<T> event = new ModelChangedEvent<>(model);
        source.asElement().dispatchEvent(event.asEvent());
    }

}
