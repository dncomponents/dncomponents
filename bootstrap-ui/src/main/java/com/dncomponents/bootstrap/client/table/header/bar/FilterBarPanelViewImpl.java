package com.dncomponents.bootstrap.client.table.header.bar;

import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.header.HeaderFiltering;
import com.dncomponents.client.dom.DomUtil;
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
        DomUtil.setStyle(label, defaultBtnStyle);
    }

    @Override
    public void update(int size) {
        if (size > 0) {
            label.setText("Filtered by: <span class=\"badge badge-light\">" + size + "</span>" + (size > 1 ? " fields" : " field") + "\n");
            DomUtil.setStyle(label, "btn btn-danger mr-3 mb-3");
        } else {
            label.setText("Filter");
            DomUtil.setStyle(label, defaultBtnStyle);
        }
    }
}
