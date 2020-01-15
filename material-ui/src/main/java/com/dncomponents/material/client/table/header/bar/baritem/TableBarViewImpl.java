package com.dncomponents.material.client.table.header.bar.baritem;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.table.headers.bar.TableBarView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class TableBarViewImpl implements TableBarView {

    @UiField
    HTMLElement root;

    HtmlBinder uiBinder = HtmlBinder.get(TableBarViewImpl.class, this);

    public TableBarViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TableBarViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public void add(HTMLElement element) {
        asElement().appendChild(element);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
