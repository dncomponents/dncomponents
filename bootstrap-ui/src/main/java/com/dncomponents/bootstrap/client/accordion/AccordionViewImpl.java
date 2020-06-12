package com.dncomponents.bootstrap.client.accordion;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.accordion.AccordionView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class AccordionViewImpl implements AccordionView {

    HtmlBinder uiBinder = HtmlBinder.get(AccordionViewImpl.class, this);

    @UiField
    HTMLElement root;

    public AccordionViewImpl() {
        uiBinder.bind();
    }

    public AccordionViewImpl(String templateContent) {
        uiBinder.setTemplateContent(templateContent);
        uiBinder.bind();
    }

    public AccordionViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public void addItem(IsElement item) {
        root.appendChild(item.asElement());
    }

    public void removeItem(IsElement item) {
        root.removeChild(item.asElement());
    }

    @Override
    public void clearAll() {
        root.innerHTML = "";
    }

}