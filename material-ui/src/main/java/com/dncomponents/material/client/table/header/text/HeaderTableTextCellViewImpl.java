package com.dncomponents.material.client.table.header.text;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableTextCellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class HeaderTableTextCellViewImpl implements HeaderTableTextCellView {

    @UiField
    HTMLElement root;

    HtmlBinder uiBinder = HtmlBinder.get(HeaderTableTextCellViewImpl.class, this);

    public HeaderTableTextCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public HeaderTableTextCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setText(String text) {
        asElement().innerHTML = text;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
