package com.dncomponents.bootstrap.client.tab;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.bootstrap.client.tab.helper.Orientation;
import com.dncomponents.bootstrap.client.tab.helper.TabType;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tab.TabUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

public class TabUiFactory {


    public static class DefaultUiFactory extends AbstractPluginHelper implements ViewFactory<TabUi> {

        private static DefaultUiFactory instance;

        private DefaultUiFactory() {
            arguments.put(TabViewImpl.Builder.typeId, TabType.lookUp.toStringList());
            arguments.put(TabViewImpl.Builder.orientationId, Orientation.lookUp.toStringList());
        }

        public static DefaultUiFactory getInstance() {
            if (instance == null)
                instance = new DefaultUiFactory();
            return instance;
        }

        @Override
        public TabUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            TabType type = TabType.lookUp.getValue(attributes.get(TabViewImpl.Builder.typeId));
            Orientation orientation = Orientation.lookUp.getValue(attributes.get(TabViewImpl.Builder.orientationId));
            return TabUiImpl.Builder.get()
                    .template(templateElement)
                    .orientation(orientation)
                    .type(type)
                    .build();
        }

        @Override
        public String getId() {
            return TabUiImpl.UI_ID;
        }

        @Override
        public Class getClazz() {
            return TabUiImpl.class;
        }
    }
}