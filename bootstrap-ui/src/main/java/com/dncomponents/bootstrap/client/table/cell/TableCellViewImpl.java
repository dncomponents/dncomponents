package com.dncomponents.bootstrap.client.table.cell;

import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.cell.BaseCellViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TableCellViewImpl extends BaseCellViewImpl {
    public TableCellViewImpl() {
    }

    HtmlBinder uiBinder = HtmlBinder.get(TableCellViewImpl.class, this);

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
