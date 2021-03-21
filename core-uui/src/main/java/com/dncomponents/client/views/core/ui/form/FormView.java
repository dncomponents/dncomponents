package com.dncomponents.client.views.core.ui.form;

import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;

public interface FormView<M> extends View {
    void addItem(HTMLElement asElement);

    void addSaveHandler(ClickHandler e);

    HasValue<M> getHasValue();

    void setSubmitButtonText(String text);

    void showSubmitButton(boolean b);
}
