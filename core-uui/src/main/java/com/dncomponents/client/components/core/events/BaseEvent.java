package com.dncomponents.client.components.core.events;

import elemental2.dom.CustomEvent;
import elemental2.dom.CustomEventInit;

public abstract class BaseEvent implements IsEvent {
    protected CustomEvent customEvent;

    public BaseEvent(String type) {
        CustomEventInit init = CustomEventInit.create();
        init.setDetail(this);
        customEvent = new CustomEvent(type, init);
    }

    @Override
    public CustomEvent asEvent() {
        return customEvent;
    }
}
