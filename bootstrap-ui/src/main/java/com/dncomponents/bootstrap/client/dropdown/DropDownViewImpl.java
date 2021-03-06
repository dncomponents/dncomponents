package com.dncomponents.bootstrap.client.dropdown;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.popover.Popper;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.dropdown.DropDownView;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.Node;

/**
 * @author nikolasavic
 */
@UiTemplate
public class DropDownViewImpl implements DropDownView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement button;
    @UiField
    HTMLElement dropDownMenu;
    @UiStyle
    String dropDownMenuShowStyle;

    Popper popper;

    HtmlBinder uiBinder = HtmlBinder.get(DropDownViewImpl.class, this);

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
            dropDownMenu.classList.add(dropDownMenuShowStyle);
        else
            dropDownMenu.classList.remove(dropDownMenuShowStyle);
        if (popper == null) {
            Popper.Defaults def = Popper.Defaults.create();
            def.setPlacement("bottom");
            popper = new Popper(button, dropDownMenu, def);
        }
        popper.update();
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
        button.innerHTML = "";
        button.appendChild(content);
    }

    @Override
    public IsElement getRelativeElement() {
        return new IsElement() {
            @Override
            public HTMLElement asElement() {
                return button;
            }
        };
    }

    @Override
    public void setButtonContent(String content) {
        button.innerHTML = content;
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
