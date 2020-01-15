package com.dncomponents.material.client.multilevel;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.MouseEnterHandler;
import com.dncomponents.client.dom.handlers.MouseLeaveHandler;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemMultiLevelParentView;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemViewSlots;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class DropDownItemMultiLevelParentViewImpl implements DropDownItemMultiLevelParentView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement textPanel;

    HtmlBinder uiBinder = HtmlBinder.get(DropDownItemMultiLevelParentViewImpl.class, this);

    @Override
    public void setContent(String content) {
        textPanel.innerHTML = "";
        textPanel.innerHTML = content;
    }

    @Override
    public void setHtmlContent(HTMLElement content) {
        textPanel.innerHTML = "";
        textPanel.appendChild(content);
    }

    public DropDownItemMultiLevelParentViewImpl(HTMLTemplateElement templateElement) {
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
                return textPanel;
            }
        };
    }

    @Override
    public void addMouseLeaveHandler(MouseLeaveHandler mouseLeaveHandler) {
        mouseLeaveHandler.addTo(asElement());
    }

    @Override
    public void addMouseEnterHandler(MouseEnterHandler mouseEnterHandler) {
        mouseEnterHandler.addTo(asElement());
    }
}