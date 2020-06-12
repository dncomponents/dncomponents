package com.dncomponents.material.client.list;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.cell.BaseCellViewImpl;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class ListCellViewImpl extends BaseCellViewImpl {

    @UiField
    HTMLElement valuePanel;

    public ListCellViewImpl(HTMLTemplateElement templateElement) {
        HtmlBinder uiBinder = HtmlBinder.get(ListCellViewImpl.class, this);
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public HTMLElement getValuePanel() {
        return valuePanel;
    }
}
