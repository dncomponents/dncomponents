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

package com.dncomponents.material.client.tree;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tree.TreeUi;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;


public class TreeViewFactory {

    public static class DefaultTreeViewFactory extends AbstractPluginHelper implements ViewFactory<TreeUi> {

        private static DefaultTreeViewFactory instance;

        private DefaultTreeViewFactory() {
        }

        public static DefaultTreeViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultTreeViewFactory();
            return instance;
        }

        @Override
        public TreeUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).tree;
            return new TreeUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return TreeUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TreeUiImpl.class;
        }
    }

}