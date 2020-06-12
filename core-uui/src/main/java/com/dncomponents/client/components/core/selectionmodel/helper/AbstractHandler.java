package com.dncomponents.client.components.core.selectionmodel.helper;


import com.dncomponents.client.components.core.events.HasHandlers;
import com.dncomponents.client.dom.DomUtil;
import elemental2.dom.CustomEvent;
import elemental2.dom.HTMLElement;

public class AbstractHandler implements HasHandlers {

    private HTMLElement bus;

    protected HTMLElement ensureHandlers() {
        if (bus == null)
            bus = DomUtil.createDiv();
        return bus;
    }

    @Override
    public void fireEvent(CustomEvent event) {
        ensureHandlers().dispatchEvent(event);
    }
}
