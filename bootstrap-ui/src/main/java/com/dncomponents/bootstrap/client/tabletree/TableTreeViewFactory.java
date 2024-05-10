/*
 * Copyright 2024 dncomponents
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

package com.dncomponents.bootstrap.client.tabletree;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tabletree.TableTreeUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;


public class TableTreeViewFactory {

    public static class DefaultTableTreeViewFactory extends AbstractPluginHelper implements ViewFactory<TableTreeUi> {

        private static DefaultTableTreeViewFactory instance;

        private DefaultTableTreeViewFactory() {
        }

        public static DefaultTableTreeViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultTableTreeViewFactory();
            return instance;
        }

        @Override
        public TableTreeUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                return new TableTreeUiImpl();
            else
                return new TableTreeUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return TableTreeUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TableTreeUiImpl.class;
        }
    }

}
