package com.dncomponents.material.client.table.cell;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.client.components.table.columnclasses.rowexpandercolumn.RowDetailsCellView;
import com.dncomponents.client.components.core.HtmlBinder;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@Component
public class MdcRowDetailsCellViewImpl extends TableCellViewImpl implements RowDetailsCellView {

    @UiField
    HTMLElement openClosedElement;

    HtmlBinder uiBinder = HtmlBinder.create(MdcRowDetailsCellViewImpl.class, this);

    public MdcRowDetailsCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public MdcRowDetailsCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setOpened(boolean b) {
        if (!b)
            openClosedElement.innerHTML = "chevron_right";
        else
            openClosedElement.innerHTML = "expand_more";
    }

    @Override
    public HTMLElement getValuePanel() {
        return asElement();
    }
}
