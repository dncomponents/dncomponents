package com.dncomponents.material.client.autocomplete;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.views.core.ui.autocomplete.BaseAutocompleteView;
import elemental2.dom.CustomEvent;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public abstract class BaseAutocompleteViewImpl<T> implements BaseAutocompleteView<T> {

    @UiField
    protected HTMLElement root;
    @UiField
    protected TextBox textBox;
    @UiField
    HTMLElement listPanel;
    @UiField
    HTMLElement button;
    @UiField
    HTMLElement buttonText;

    @Override
    public void addKeyUpHandler(KeyUpHandler keyUpHandler) {
        textBox.addHandler(keyUpHandler);
    }

    @Override
    public HandlerRegistration addButtonClickHandler(ClickHandler clickHandler) {
        return clickHandler.addTo(button);
    }

    @Override
    public void setStringValue(String value) {
        if (buttonText != null) {
            if (value == null)
                value = "Choose...";
            buttonText.innerHTML = value;
        }
    }

    @Override
    public void showListPanel(boolean b, Command done) {
        if (b) {
            root.classList.add("mdc-select--focused");
            root.classList.add("mdc-select--activated");
            listPanel.classList.add("mdc-menu-surface--open");
//            listPanel.style.display = "block";
        } else {
            root.classList.remove("mdc-select--focused");
            root.classList.remove("mdc-select--activated");
            listPanel.classList.remove("mdc-menu-surface--open");
        }
        if (done != null) done.execute();
    }

    @Override
    public void setTextBoxFocused(boolean b) {
        textBox.setFocus(b);
    }


    @Override
    public String getTextBoxCurrentValue() {
        return textBox.getValueFromView();
    }

    @Override
    public void setTextBoxCurrentValue(String value) {
        textBox.setValue(null, true);
    }


    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public void fireEvent(CustomEvent event) {
        asElement().dispatchEvent(event);
    }
}