package com.dncomponents.bootstrap.client.tab;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.tab.TabItemView;
import com.dncomponents.client.views.core.ui.tab.TabItemViewSlots;
import com.google.gwt.user.client.Command;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

@UiTemplate
public class TabItemViewImpl implements TabItemView {

    @UiField
    HTMLElement tabItemNav;
    @UiField
    HTMLElement tabItemPanel;
    @UiField
    HTMLElement tabItemContent;
    @UiStyle
    String showTabContentStyle;
    @UiStyle
    String active;


    HtmlBinder uiBinder = HtmlBinder.get(TabItemViewImpl.class, this);

    public TabItemViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public void addItemSelectedHandler(EventListener handler) {
        tabItemPanel.addEventListener("click", handler);
    }

    @Override
    public void select(boolean b) {
        String[] ll = showTabContentStyle.split(" ");
        if (b) {
            tabItemPanel.classList.add(active);
            tabItemContent.classList.add(ll);
        } else {
            tabItemPanel.classList.remove(active);
            tabItemContent.classList.remove(ll);
        }
    }

    @Override
    public void setItemTitle(String html) {
        tabItemPanel.textContent = html;
    }

    @Override
    public void setItemTitleHtml(String html) {
        tabItemPanel.innerHTML = html;
    }

    @Override
    public void setItemTitle(HTMLElement html) {
        tabItemPanel.innerHTML = html.innerHTML;
    }

    @Override
    public void setItemContent(String html) {
        tabItemContent.textContent = html;
    }

    @Override
    public void setItemContent(HTMLElement htmlElement) {
        tabItemContent.innerHTML = htmlElement.innerHTML;
    }

    @Override
    public void setImmediate(Command command) {

    }

    @Override
    public HTMLElement getTabItemNav() {
        return tabItemNav;
    }

    @Override
    public HTMLElement getTabItemContent() {
        return tabItemContent;
    }

    TabItemViewSlots tabItemViewSlots = new TabItemViewSlots() {
        @Override
        public HTMLElement getTitle() {
            return tabItemPanel;
        }

        @Override
        public HTMLElement getContent() {
            return tabItemContent;
        }
    };

    @Override
    public TabItemViewSlots getViewSlots() {
        return tabItemViewSlots;
    }


    @Override
    public HTMLElement asElement() {
        return tabItemPanel;
    }
}