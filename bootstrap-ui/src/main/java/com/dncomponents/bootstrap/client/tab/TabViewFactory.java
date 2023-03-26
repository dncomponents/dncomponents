/*
 * Copyright 2023 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.bootstrap.client.tab;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.bootstrap.client.tab.helper.Orientation;
import com.dncomponents.bootstrap.client.tab.helper.TabType;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tab.TabUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

public class TabViewFactory {


    public static class DefaultTabViewFactory extends AbstractPluginHelper implements ViewFactory<TabUi> {

        private static DefaultTabViewFactory instance;

        private DefaultTabViewFactory() {
            arguments.put(TabViewImpl.Builder.typeId, TabType.lookUp.toStringList());
            arguments.put(TabViewImpl.Builder.orientationId, Orientation.lookUp.toStringList());
        }

        public static DefaultTabViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultTabViewFactory();
            return instance;
        }

        @Override
        public TabUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (BootstrapUi.getUi()).tabUi;
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
