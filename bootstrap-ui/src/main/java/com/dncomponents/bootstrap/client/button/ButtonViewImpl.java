package com.dncomponents.bootstrap.client.button;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.core.ui.button.ButtonView;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class ButtonViewImpl implements ButtonView {

    @UiField
    HTMLButtonElement root;
    @UiStyle
    String disabledStyle;

    HtmlBinder uiBinder = HtmlBinder.get(ButtonViewImpl.class, this);

    public ButtonViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public ButtonViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public void setText(String text) {
        root.innerHTML = text;
    }

    @Override
    public void setHTML(String html) {
        root.innerHTML = html;
    }

    @Override
    public String getHTML() {
        return root.innerHTML;
    }

    @Override
    public String getText() {
        return root.textContent;
    }

    @Override
    public void setEnabled(boolean enabled) {

        if (enabled) {
            root.removeAttribute("disabled");
        } else {
            root.setAttribute("disabled", "");
        }
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public MainViewSlots getViewSlots() {
        return this::asElement;
    }


    protected static final String VIEW_ID = "DEFAULT";

}
