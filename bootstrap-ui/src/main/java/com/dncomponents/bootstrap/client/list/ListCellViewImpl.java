package com.dncomponents.bootstrap.client.list;

import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.cell.BaseCellViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class ListCellViewImpl extends BaseCellViewImpl {


    public ListCellViewImpl(HTMLTemplateElement templateElement) {
        HtmlBinder uiBinder = HtmlBinder.get(ListCellViewImpl.class, this);
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public HTMLElement getValuePanel() {
        return asElement();
    }
}
