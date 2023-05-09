package com.dncomponents.material.client.dropdown;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.MainViewSlotsImpl;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@Component
public class DropDownItemViewImpl implements DropDownItemView {

    @UiField
    HTMLElement root;
    HtmlBinder uiBinder = HtmlBinder.create(DropDownItemViewImpl.class, this);

    @Override
    public void setContent(String content) {
        asElement().innerHTML = content;
    }

    @Override
    public void setHtmlContent(HTMLElement content) {
        asElement().innerHTML = "";
        asElement().appendChild(content);
    }

    public DropDownItemViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void addClickHandler(ClickHandler clickHandler) {
        clickHandler.addTo(asElement());
    }

    @Override
    public void setActive(boolean active) {
        if (active)
            asElement().classList.add("active");
        else
            asElement().classList.remove("active");
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public MainViewSlots getViewSlots() {
        return new MainViewSlotsImpl(asElement());
    }
}
