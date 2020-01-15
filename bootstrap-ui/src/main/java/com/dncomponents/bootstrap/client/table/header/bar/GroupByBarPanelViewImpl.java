package com.dncomponents.bootstrap.client.table.header.bar;

import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.header.HeaderGrouping;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.GroupByBarPanelView;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class GroupByBarPanelViewImpl extends BaseBarPanelViewImpl<HeaderGrouping> implements GroupByBarPanelView {


    HtmlBinder uiBinder = HtmlBinder.get(GroupByBarPanelViewImpl.class, this);

    public GroupByBarPanelViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }

    public GroupByBarPanelViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }


    CheckBox expandAll = new CheckBox("expand all");


    private void init() {
        bind();
        label.setText("Group");
        expandAll.setValue(true);
        DomUtil.setStyle(label, defaultBtnStyle);
    }

    @Override
    public void clear() {
        super.clear();
        contentPanel.appendChild(expandAll.asElement());
    }

    @Override
    public void update(int size) {
        if (size > 0) {
            label.setText("Grouped by: <span class=\"badge badge-light\">" + size + "</span>" + (size > 1 ? " fields" : " field") + "\n");
            DomUtil.setStyle(label, "btn btn-warning mr-3 mb-3");
        } else {
            label.setText("Group");
            DomUtil.setStyle(label, defaultBtnStyle);
        }
    }

    @Override
    public void addExpandAllHandler(ValueChangeHandler<Boolean> handler) {
        expandAll.addValueChangeHandler(handler);
    }
}
