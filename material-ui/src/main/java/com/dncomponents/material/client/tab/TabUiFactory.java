package com.dncomponents.material.client.tab;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tab.TabUi;
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.material.client.tab.helper.Orientation;
import com.dncomponents.material.client.tab.helper.TabType;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

public class TabUiFactory {


    public static class DefaultTabFactory extends AbstractPluginHelper implements ViewFactory<TabUi> {

        private static DefaultTabFactory instance;

        private DefaultTabFactory() {
            arguments.put(TabViewImpl.Builder.typeId, TabType.lookUp.toStringList());
            arguments.put(TabViewImpl.Builder.orientationId, Orientation.lookUp.toStringList());
        }

        public static DefaultTabFactory getInstance() {
            if (instance == null)
                instance = new DefaultTabFactory();
            return instance;
        }

        @Override
        public TabUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).tabUi;
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