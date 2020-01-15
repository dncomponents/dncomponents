package com.dncomponents.bootstrap.client.table.header.bar;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.table.header.bar.baritem.FilterBarItemViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.FilterBarPanelUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.FilterBarPanelView;
import com.dncomponents.client.views.core.ui.table.headers.bar.panelitems.FilterBarItemView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class FilterBarPanelUiImpl implements FilterBarPanelUi {
    @UiField("table-bar-item")
    HTMLTemplateElement tableBarItemUi;
    @UiField("table-bar-panel")
    HTMLTemplateElement tableBarPanelUi;
    FilterBarPanelView filterBarPanelView;
    HtmlBinder uiBinder = HtmlBinder.get(FilterBarPanelUiImpl.class, this);

    public FilterBarPanelUiImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public FilterBarPanelUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public FilterBarItemView getFilterBarItemView() {
        return new FilterBarItemViewImpl(tableBarItemUi);
    }

    @Override
    public FilterBarPanelView getRootView() {
        if (filterBarPanelView == null)
            filterBarPanelView = new FilterBarPanelViewImpl(tableBarPanelUi);
        return filterBarPanelView;
    }
}
