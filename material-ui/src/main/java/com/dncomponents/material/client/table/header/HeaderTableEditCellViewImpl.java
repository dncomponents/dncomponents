package com.dncomponents.material.client.table.header;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableEditCellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class HeaderTableEditCellViewImpl implements HeaderTableEditCellView {

    @UiField
    HTMLElement root;

    @UiField
    HTMLElement addBtn;

    HtmlBinder uiBinder = HtmlBinder.get(HeaderTableEditCellViewImpl.class, this);

    public HeaderTableEditCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public HeaderTableEditCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void addAddHandler(ClickHandler clickHandler) {
        clickHandler.addTo(addBtn);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
