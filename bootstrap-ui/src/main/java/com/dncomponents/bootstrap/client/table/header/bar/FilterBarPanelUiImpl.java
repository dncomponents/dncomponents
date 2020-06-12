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
    @UiField
    HTMLTemplateElement tableBarItem;
    @UiField
    HTMLTemplateElement tableBarPanel;
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
        return new FilterBarItemViewImpl(tableBarItem);
    }

    @Override
    public FilterBarPanelView getRootView() {
        if (filterBarPanelView == null)
            filterBarPanelView = new FilterBarPanelViewImpl(tableBarPanel);
        return filterBarPanelView;
    }
}
