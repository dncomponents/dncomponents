package com.dncomponents.bootstrap.client.table.footer;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.pcg.cell.CellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class FooterCellViewImpl implements CellView {

    @UiField
    HTMLElement root;

    HtmlBinder uiBinder = HtmlBinder.get(FooterCellViewImpl.class, this);

    public FooterCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public FooterCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public HTMLElement asElement() {
        return root;
    }
}
