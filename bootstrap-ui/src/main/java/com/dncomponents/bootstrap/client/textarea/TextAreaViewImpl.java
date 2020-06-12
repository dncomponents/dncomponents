package com.dncomponents.bootstrap.client.textarea;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.HTMLTextAreaElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TextAreaViewImpl implements TextBoxView {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLTextAreaElement root;
    HtmlBinder uiBinder = HtmlBinder.get(TextAreaViewImpl.class, this);

    public TextAreaViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public String getValue() {
        return asElement().value;
    }

    @Override
    public void setValue(String value) {
        asElement().value = value;
    }

    @Override
    public void addOnInputChangeHandler(EventListener listener) {

    }

    @Override
    public void setError(boolean b) {
        if (b)
            root.classList.add("invalid");
        else
            root.classList.remove("invalid");
    }

    @Override
    public void setErrorMessage(String errorMessage) {

    }

    @Override
    public void setPlaceHolder(String placeHolder) {
        root.setAttribute("placeholder", placeHolder);
    }

    @Override
    public void addOnBlurHandler(OnBlurHandler handler) {
        handler.addTo(asElement());
    }

    @Override
    public void addOnKeyUpHandler(KeyUpHandler handler) {
        handler.addTo(asElement());
    }

    @Override
    public HTMLTextAreaElement asElement() {
        return root;
    }
}
