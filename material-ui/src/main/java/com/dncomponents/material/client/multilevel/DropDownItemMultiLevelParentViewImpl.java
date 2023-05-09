package com.dncomponents.material.client.multilevel;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.MouseEnterHandler;
import com.dncomponents.client.dom.handlers.MouseLeaveHandler;
import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.MainViewSlotsImpl;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemMultiLevelParentView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class DropDownItemMultiLevelParentViewImpl implements DropDownItemMultiLevelParentView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement textPanel;

    HtmlBinder uiBinder = HtmlBinder.create(DropDownItemMultiLevelParentViewImpl.class, this);

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
    public MainViewSlots getViewSlots() {
        return new MainViewSlotsImpl(textPanel);
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
