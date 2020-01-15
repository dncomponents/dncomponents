package com.dncomponents.material.client.table.header.bar;

import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.header.HeaderFiltering;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.FilterBarPanelView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class FilterBarPanelViewImpl extends BaseBarPanelViewImpl<HeaderFiltering> implements FilterBarPanelView {

    HtmlBinder uiBinder = HtmlBinder.get(FilterBarPanelViewImpl.class, this);

    public FilterBarPanelViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }

    public FilterBarPanelViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    private void init() {
        bind();
        label.setText("Filter");
        label.asElement().className = ("mdc-button mdc-button--outlined ");
    }

    @Override
    public void update(int size) {
        if (size > 0)
            label.setText("Filtered by: " + getChip(size + "") + (size > 1 ? "fields" : "field") + "\n");
        else
            label.setText("Filter");
    }
}
