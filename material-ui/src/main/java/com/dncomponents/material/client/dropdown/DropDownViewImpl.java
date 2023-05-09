package com.dncomponents.material.client.dropdown;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.dropdown.DropDownView;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@Component
public class DropDownViewImpl implements DropDownView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement button;
    @UiField
    HTMLElement buttonText;
    @UiField
    HTMLElement dropDownMenuPanel;
    @UiField
    HTMLElement dropDownMenu;
    @UiField
    String dropDownMenuShowStyle;

    HtmlBinder uiBinder = HtmlBinder.create(DropDownViewImpl.class, this);

    public DropDownViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public DropDownViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public void addClickOnButton(BaseEventListener handler) {
        handler.addTo(button);
    }

    @Override
    public void showList(boolean b) {
        if (b)
            dropDownMenuPanel.classList.add(dropDownMenuShowStyle);
        else
            dropDownMenuPanel.classList.remove(dropDownMenuShowStyle);
    }

    @Override
    public void addItem(IsElement item) {
        dropDownMenu.appendChild(item.asElement());
    }

    @Override
    public void removeItem(IsElement item) {
        item.asElement().remove();
    }

    @Override
    public void setButtonHtmlContent(HTMLElement content) {
        buttonText.innerHTML = "";
        buttonText.appendChild(content);
    }

    @Override
    public IsElement getRelativeElement() {
        return () -> button;
    }

    @Override
    public void setButtonContent(String content) {
        buttonText.innerHTML = content;
    }

    @Override
    public HandlerRegistration addClickOutOfButton(ClickHandler clickHandler) {
        return clickHandler.addTo(DomGlobal.document.body);
    }

    @Override
    public void addDropDownPanel(IsElement dropDownPanel) {

    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
