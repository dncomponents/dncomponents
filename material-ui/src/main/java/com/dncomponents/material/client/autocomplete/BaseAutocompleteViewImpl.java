package com.dncomponents.material.client.autocomplete;

import com.dncomponents.UiField;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.views.core.ui.autocomplete.BaseAutocompleteView;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public abstract class BaseAutocompleteViewImpl<T> implements BaseAutocompleteView<T> {

    @UiField
    HTMLElement root;
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
    public void addButtonClickHandler(ClickHandler clickHandler) {
        DomUtil.addHandler(button, clickHandler);
//        button.addClickHandler(clickHandler);
    }

    @Override
    public void setStringValue(String value) {
        if (value == null)
            value = "Choose...";
        buttonText.innerHTML = value;
    }

    @Override
    public void showListPanel(boolean b) {
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
}