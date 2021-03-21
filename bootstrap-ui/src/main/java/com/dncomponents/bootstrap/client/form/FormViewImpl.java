package com.dncomponents.bootstrap.client.form;

import com.dncomponents.UiField;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.form.FormView;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLFormElement;
import elemental2.dom.HTMLTemplateElement;

public class FormViewImpl implements FormView {
    @UiField
    HTMLElement root;
    @UiField
    HTMLDivElement mainPanel;
    @UiField
    Button submitBtn;

    HtmlBinder uiBinder = HtmlBinder.get(FormViewImpl.class, this);

    public FormViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public void addItem(HTMLElement asElement) {
        mainPanel.appendChild(asElement);
    }

    @Override
    public void addSaveHandler(ClickHandler e) {
        submitBtn.addClickHandler(e);
    }

    @Override
    public HasValue getHasValue() {
        return null;
    }

    @Override
    public void setSubmitButtonText(String text) {
        submitBtn.setText(text);
    }

    @Override
    public void showSubmitButton(boolean b) {
        if (b)
            submitBtn.asElement().style.display = "block";
        else
            submitBtn.asElement().style.display = "none";
    }
}
