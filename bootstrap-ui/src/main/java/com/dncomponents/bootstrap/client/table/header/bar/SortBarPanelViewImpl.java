package com.dncomponents.bootstrap.client.table.header.bar;

import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.header.HeaderSorting;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.SortBarPanelView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class SortBarPanelViewImpl extends BaseBarPanelViewImpl<HeaderSorting> implements SortBarPanelView {

    HtmlBinder uiBinder = HtmlBinder.get(SortBarPanelViewImpl.class, this);

    public SortBarPanelViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }

    public SortBarPanelViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    private void init() {
        bind();
        label.setText("Sort");
        DomUtil.setStyle(label, defaultBtnStyle);
    }

    @Override
    public void update(int size) {
        if (size > 0) {
            label.setText("Sorted by: <span class=\"badge badge-light\">" + size + "</span>" + (size > 1 ? " fields" : " field") + "\n");
            DomUtil.setStyle(label, "btn btn-success mr-3 mb-3");
        } else {
            label.setText("Sort");
            DomUtil.setStyle(label, defaultBtnStyle);
        }
    }

}
