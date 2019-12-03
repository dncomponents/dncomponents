package com.dncomponents.bootstrap.client.accordion;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.accordion.AccordionItemView;
import com.dncomponents.client.views.core.ui.accordion.AccordionItemViewSlots;
import com.google.gwt.user.client.Command;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

@UiTemplate
public class AccordionItemViewImpl implements AccordionItemView {

    @UiField
    HTMLElement root;

    @UiField
    HTMLElement accordionItemTitle;

    @UiField
    HTMLElement accordionItemTitlePanel;

    @UiField
    HTMLElement contentPanel;

    @UiField
    HTMLElement contentPanelParent;

    @UiStyle
    String showContentStyle;


    HtmlBinder uiBinder = HtmlBinder.get(AccordionItemViewImpl.class, this);

    public AccordionItemViewImpl(String templateContent) {
        uiBinder.setTemplateContent(templateContent);
        uiBinder.bind();
    }


    public AccordionItemViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public void addItemSelectedHandler(EventListener handler) {
        accordionItemTitlePanel.addEventListener("click", handler);
    }

    @Override
    public void select(boolean b) {
        if (b)
            contentPanelParent.classList.add(showContentStyle);
        else
            contentPanelParent.classList.remove(showContentStyle);
    }

    @Override
    public void setItemTitle(String html) {
        accordionItemTitle.innerHTML = "";
        accordionItemTitle.innerHTML = html;
    }

    @Override
    public void setItemTitle(HTMLElement html) {
        accordionItemTitle.innerHTML = "";
        accordionItemTitle.appendChild(html);
    }

    @Override
    public void setItemContent(String html) {
        contentPanel.innerHTML = "";
        contentPanel.innerHTML = html;
    }

    @Override
    public void setItemContent(HTMLElement htmlElement) {
        contentPanel.innerHTML = htmlElement.innerHTML;
        contentPanel.innerHTML = htmlElement.innerHTML;
    }

    @Override
    public void setImmediate(Command command) {

    }

    @Override
    public String getTitle() {
        return accordionItemTitle.innerHTML;
    }

    @Override
    public String getContent() {
        return contentPanel.innerHTML;
    }

    @Override
    public AccordionItemViewSlots getViewSlots() {
        return accordionItemViewSlots;
    }

    private AccordionItemViewSlots accordionItemViewSlots = new AccordionItemViewSlots() {
        @Override
        public HTMLElement getTitle() {
            return accordionItemTitle;
        }

        @Override
        public HTMLElement getContent() {
            return contentPanel;
        }
    };


    @Override
    public HTMLElement asElement() {
        return root;
    }
}
