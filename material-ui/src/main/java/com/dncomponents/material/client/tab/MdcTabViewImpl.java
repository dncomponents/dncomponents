package com.dncomponents.material.client.tab;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.TemplateParser;
import com.dncomponents.client.views.core.ui.tab.TabView;
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.material.client.tab.helper.Orientation;
import com.dncomponents.material.client.tab.helper.TabType;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

public class MdcTabViewImpl implements TabView {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLElement root;
    @UiField
    HTMLElement tabPanel;
    @UiField
    HTMLElement tabContentPanel;

    HtmlBinder uiBinder = HtmlBinder.get(MdcTabViewImpl.class, this);

    public MdcTabViewImpl(HTMLTemplateElement templateElement) {
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


        public MdcTabViewImpl build() {
            MdcTabViewImpl tabView;
            if (type == null)
                type = TabType.TAB;
            if (orientation == Orientation.VERTICAL) {
                TemplateParser template = new TemplateParser(MaterialUi.getUi().tabUi, true);
                tabView = new MdcTabViewImpl(template.getElement(MdcTabUiImpl.TAB_VERTICAL));
            } else {
                TemplateParser template = new TemplateParser(MaterialUi.getUi().tabUi, true);
                tabView = new MdcTabViewImpl(template.getElement(MdcTabUiImpl.TAB));
            }
            return tabView;
        }
    }

}