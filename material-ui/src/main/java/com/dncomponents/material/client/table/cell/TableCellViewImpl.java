package com.dncomponents.material.client.table.cell;

import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.cell.BaseCellViewImpl;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@Component
public class TableCellViewImpl extends BaseCellViewImpl {
    public TableCellViewImpl() {
    }

    HtmlBinder uiBinder = HtmlBinder.create(TableCellViewImpl.class, this);

    public TableCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }


    public TableCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    private void init() {
        root.setAttribute("tabindex", 0);
    }

    @Override
    public HTMLElement getValuePanel() {
        return asElement();
    }
}
