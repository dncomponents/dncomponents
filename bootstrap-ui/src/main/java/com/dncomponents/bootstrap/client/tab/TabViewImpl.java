package com.dncomponents.bootstrap.client.tab;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.TemplateParser;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.bootstrap.client.tab.helper.Orientation;
import com.dncomponents.bootstrap.client.tab.helper.TabType;
import com.dncomponents.client.views.core.HasStyle;
import com.dncomponents.client.views.core.ui.tab.TabView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

public class TabViewImpl implements TabView {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLElement root;
    @UiField
    HTMLElement tabPanel;
    @UiField
    HTMLElement tabContentPanel;

    HtmlBinder uiBinder = HtmlBinder.get(TabViewImpl.class, this);

    public TabViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public void addItem(HTMLElement tabItem, HTMLElement tabContent) {
        tabPanel.appendChild(tabItem);
        tabContentPanel.appendChild(tabContent);
    }

    @Override
    public void removeItem(HTMLElement tabItem, HTMLElement tabContent) {
        tabItem.remove();
        tabContent.remove();
    }

    static class Builder {

        private static final String BASE_STYLE = "nav";

        private Orientation orientation;
        private TabType type;
        //parser
        protected static final String orientationId = "orientation";
        protected static final String typeId = "type";

        private Builder() {
        }

        public static Builder get() {
            return new Builder();
        }

        public Builder orientation(Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder type(TabType type) {
            this.type = type;
            return this;
        }


        public TabViewImpl build() {
            TabViewImpl tabView;
            if (type == null)
                type = TabType.TAB;
            if (orientation == Orientation.VERTICAL) {
                TemplateParser template = new TemplateParser(BootstrapUi.getUi().tabUi, true);
                tabView = new TabViewImpl(template.getElement(TabUiImpl.TAB_VERTICAL));
            } else {
                TemplateParser template = new TemplateParser(BootstrapUi.getUi().tabUi, true);
                tabView = new TabViewImpl(template.getElement(TabUiImpl.TAB));
            }
            String style = BASE_STYLE + HasStyle.appendString(orientation) + HasStyle.appendString(type);
            tabView.tabPanel.className = style;
            return tabView;
        }
    }

}
