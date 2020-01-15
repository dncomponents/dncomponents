package com.dncomponents.bootstrap.client.table.header;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.header.SortingDirection;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.table.headers.HeaderTableFilterCellView;
import elemental2.dom.Event;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class HeaderTableFilterCellViewImpl implements HeaderTableFilterCellView {

    @UiField
    HTMLElement root;
    @UiField("text-panel")
    HTMLElement textPanel;
    @UiField("filter-icon-panel")
    HTMLElement filterIconPanel;
    @UiField("filter-panel")
    HTMLElement filterPanelElement;
    @UiField("icon-panel")
    HTMLElement sortIconPanel;

    HtmlBinder uiBinder = HtmlBinder.get(HeaderTableFilterCellViewImpl.class, this);

    public HeaderTableFilterCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public HeaderTableFilterCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setFilterPanel(IsElement filterPanel) {
        DomUtil.setVisible(filterIconPanel, false);
        DomUtil.setVisible(sortIconPanel, false);
        filterPanelElement.appendChild(filterPanel.asElement());
        filterPanel.asElement().addEventListener(ClickHandler.TYPE, (ClickHandler) Event::stopPropagation);
    }


    @Override
    public void setFilterIconVisible(boolean b) {
        DomUtil.setVisible(filterIconPanel, b);
    }


    @Override
    public void setSorted(SortingDirection direction) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setActive(boolean b) {

    }

    @Override
    public void setSortPresenter(SortPresenter presenter) {

    }

    @Override
    public void setSortIconText(String iconText) {

    }

    @Override
    public void setGroupOrder(int order) {

    }

    @Override
    public String getText() {
        return textPanel.innerHTML;
    }

    @Override
    public void setText(String text) {
        textPanel.innerHTML = text;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
