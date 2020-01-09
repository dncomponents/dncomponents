package com.dncomponents.material.client.tab;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tab.TabUi;
import com.dncomponents.material.client.tab.helper.Orientation;
import com.dncomponents.material.client.tab.helper.TabType;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

public class TabUiFactory {


    public static class DefaultUiFactory extends AbstractPluginHelper implements ViewFactory<TabUi> {

        private static DefaultUiFactory instance;

        private DefaultUiFactory() {
            arguments.put(MdcTabViewImpl.Builder.typeId, TabType.lookUp.toStringList());
            arguments.put(MdcTabViewImpl.Builder.orientationId, Orientation.lookUp.toStringList());
        }

        public static DefaultUiFactory getInstance() {
            if (instance == null)
                instance = new DefaultUiFactory();
            return instance;
        }

        @Override
        public TabUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            TabType type = TabType.lookUp.getValue(attributes.get(MdcTabViewImpl.Builder.typeId));
            Orientation orientation = Orientation.lookUp.getValue(attributes.get(MdcTabViewImpl.Builder.orientationId));
            return MdcTabUiImpl.Builder.get()
                    .template(templateElement)
                    .orientation(orientation)
                    .type(type)
                    .build();
        }

        @Override
        public String getId() {
            return MdcTabUiImpl.UI_ID;
        }

        @Override
        public Class getClazz() {
            return MdcTabUiImpl.class;
        }
    }
}