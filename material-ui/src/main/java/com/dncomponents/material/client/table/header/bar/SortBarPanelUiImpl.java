package com.dncomponents.material.client.table.header.bar;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.SortBarPanelUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.SortBarPanelView;
import com.dncomponents.client.views.core.ui.table.headers.bar.panelitems.SortBarItemView;
import com.dncomponents.material.client.table.header.bar.baritem.SortBarItemViewImpl;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class SortBarPanelUiImpl implements SortBarPanelUi {

    @UiField("table-bar-item")
    HTMLTemplateElement tableBarItemUi;
    @UiField("table-bar-panel")
    HTMLTemplateElement tableBarPanelUi;

    SortBarPanelView sortBarPanelView;
    HtmlBinder uiBinder = HtmlBinder.get(SortBarPanelUiImpl.class, this);

    public SortBarPanelUiImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public SortBarPanelUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public SortBarItemView getSortBarItemView() {
        return new SortBarItemViewImpl(tableBarItemUi);
    }

    @Override
    public SortBarPanelView getRootView() {
        if (sortBarPanelView == null)
            sortBarPanelView = new SortBarPanelViewImpl(tableBarPanelUi);
        return sortBarPanelView;
    }
}
