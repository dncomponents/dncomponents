package com.dncomponents.material.client.accordion;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.accordion.AccordionItemView;
import com.dncomponents.client.views.core.ui.accordion.AccordionUi;
import com.dncomponents.client.views.core.ui.accordion.AccordionView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class AccordionUiImpl implements AccordionUi {

    @UiField("accordion")
    HTMLTemplateElement accordion;
    @UiField("accordion-item")
    HTMLTemplateElement accordionItem;

    AccordionView accordionView;

    HtmlBinder uiBinder = HtmlBinder.get(AccordionUiImpl.class, this);

    public AccordionUiImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public AccordionUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public AccordionItemView getAccordionItemView() {
        return new AccordionItemViewImpl(accordionItem);
    }

    @Override
    public AccordionView getRootView() {
        if (accordionView == null) {
            accordionView = new AccordionViewImpl(accordion);
        }
        return accordionView;
    }
}