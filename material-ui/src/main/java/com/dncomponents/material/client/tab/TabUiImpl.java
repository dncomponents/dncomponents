package com.dncomponents.material.client.tab;

import com.dncomponents.UiField;
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.tab.TabItemView;
import com.dncomponents.client.views.core.ui.tab.TabUi;
import com.dncomponents.client.views.core.ui.tab.TabView;
import com.dncomponents.material.client.tab.helper.Orientation;
import com.dncomponents.material.client.tab.helper.TabType;
import elemental2.dom.HTMLTemplateElement;

public class TabUiImpl implements TabUi {

    public static final String UI_ID = "default";

    public static final String TAB = "tab";
    public static final String TAB_VERTICAL = "tabVertical";

    @UiField
    HTMLTemplateElement tab;
    @UiField
    HTMLTemplateElement tabItem;

    private TabView tabView;

    HtmlBinder uiBinder = HtmlBinder.get(TabUiImpl.class, this);

    public TabUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public TabItemView getTabItemView() {
        return new TabItemViewImpl(tabItem);
    }

    @Override
    public TabView getRootView() {
        if (tabView == null)
            tabView = new TabViewImpl(tab);
        return tabView;
    }

    public static class Builder {

        private Orientation orientation;
        private TabType type;
        HTMLTemplateElement templateElement;

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

        public Builder template(HTMLTemplateElement templateElement) {
            this.templateElement = templateElement;
            return this;
        }

        public TabUiImpl build() {
            if (templateElement == null)
                templateElement = MaterialUi.getUi().tabUi;
            TabUiImpl tabUi = new TabUiImpl(templateElement) {
                TabView tabView;

                @Override
                public TabView getRootView() {
                    if (tabView == null)
                        tabView = TabViewImpl.Builder.get().orientation(orientation).type(type).build();
                    return tabView;
                }
            };
            return tabUi;
        }

    }
}