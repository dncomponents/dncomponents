package com.dncomponents.bootstrap.client.textbox;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TextBoxViewImpl implements TextBoxView {

    @UiField
    HTMLInputElement root;

    HtmlBinder uiBinder = HtmlBinder.get(TextBoxViewImpl.class, this);

    public TextBoxViewImpl() {
        uiBinder.setTemplateContent("<input ui-field=\"root\" class=\"form-control\" type=\"text\">\n");
        uiBinder.bind();
    }


    public TextBoxViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TextBoxViewImpl(HTMLTemplateElement templateElement) {
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
    public void addOnBlurHandler(OnBlurHandler handler) {
        asElement().addEventListener(handler.getType(), handler);
    }

    @Override
    public void addOnKeyUpHandler(KeyUpHandler handler) {
        asElement().addEventListener(handler.getType(), handler);
    }

    @Override
    public void setError(boolean b) {
        if (b)
            root.classList.add("errorStyle");//todo
        else
            root.classList.remove("errorStyle");
    }


    @Override
    public HTMLInputElement asElement() {
        return root;
    }
}
