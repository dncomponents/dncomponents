package com.dncomponents.client.views;

import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;

public interface FocusComponentView extends View {

    default HTMLElement getFocusElement() {
        return asElement();
    }

    default void setEnabled(boolean enabled) {
        if (getFocusElement() instanceof HTMLInputElement) {
            ((HTMLInputElement) getFocusElement()).disabled = !enabled;
        } else {
            if (!enabled)
                getFocusElement().setAttribute("disabled", "true");
            else
                getFocusElement().removeAttribute("disabled");
        }
    }

    default boolean isDisabled() {
        boolean disabled;
        if (getFocusElement() instanceof HTMLInputElement) {
            disabled = ((HTMLInputElement) getFocusElement()).disabled;
        } else {
            disabled = getFocusElement().hasAttribute("disabled");
        }
        return disabled;
    }
}
