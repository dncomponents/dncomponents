package com.dncomponents.bootstrap.client.multilevel;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemView;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemViewSlots;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class DropDownItemMultiLevelViewImpl implements DropDownItemView {

    @UiField
    HTMLElement root;


    HtmlBinder uiBinder = HtmlBinder.get(DropDownItemMultiLevelViewImpl.class, this);

    @Override
    public void setContent(String content) {
        root.innerHTML = "";
        root.innerHTML = content;
    }

    @Override
    public void setHtmlContent(HTMLElement content) {
        root.innerHTML = "";
        root.appendChild(content);
    }

    public DropDownItemMultiLevelViewImpl(HTMLTemplateElement templateElement) {
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
    public DropDownItemViewSlots getViewSlots() {
        return new DropDownItemViewSlots() {
            @Override
            public HTMLElement getMainSlot() {
                return root;
            }
        };
    }
}