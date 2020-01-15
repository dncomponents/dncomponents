package com.dncomponents.bootstrap.client.autocomplete;

import com.dncomponents.UiField;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.textbox.TextBox;
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
    Button button;
    @UiField
    HTMLElement buttonText;

    @Override
    public void addKeyUpHandler(KeyUpHandler keyUpHandler) {
        textBox.addHandler(keyUpHandler);
    }

    @Override
    public void addButtonClickHandler(ClickHandler clickHandler) {
        button.addClickHandler(clickHandler);
    }

    @Override
    public void setStringValue(String value) {
        if (value == null)
            value = "Choose...";
        buttonText.innerHTML = value;
    }

    @Override
    public void showListPanel(boolean b) {
        if (b)
            listPanel.style.display = "block";
        else {
            listPanel.style.display = "none";
        }
    }

    @Override
    public void setTextBoxFocused(boolean b) {
        if (b)
            textBox.asElement().focus();
        else
            textBox.asElement().blur();
    }


    @Override
    public String getTextBoxCurrentValue() {
        return textBox.getValueFromView();
    }

    @Override
    public void setTextBoxCurrentValue(String value) {
        textBox.setValue(null, true);
    }


    //TODO virtual scroll is invoked without working with autocomplete


    @Override
    public HTMLElement asElement() {
        return root;
    }
}