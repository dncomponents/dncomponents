package com.dncomponents.bootstrap.client.button;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.button.ButtonView;
import com.dncomponents.client.views.core.ui.button.ButtonViewSlots;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class ButtonIconViewImpl implements ButtonView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement textPanel;
    @UiField
    HTMLElement iconPanel;
    @UiStyle
    String disabledStyle;

    HtmlBinder uiBinder = HtmlBinder.get(ButtonIconViewImpl.class, this);


    public ButtonIconViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public ButtonIconViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public String getText() {
        return textPanel.textContent;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled)
            root.classList.remove(disabledStyle);
        else
            root.classList.add(disabledStyle);
    }

    @Override
    public void setText(String text) {
        textPanel.textContent = text;
    }

    @Override
    public String getHTML() {
        return textPanel.innerHTML;
    }

    @Override
    public void setHTML(String html) {
        textPanel.innerHTML = html;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    ButtonViewSlots viewSlots = new ButtonViewSlots() {
        @Override
        public HTMLElement getMainSlot() {
            return textPanel;
        }
    };

    @Override
    public ButtonViewSlots getViewSlots() {
        return viewSlots;
    }


    protected static final String VIEW_ID = "ICON";

}