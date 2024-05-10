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

package com.dncomponents.material.client.checkbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

public class CheckBoxViewFactory {

    public static class DefaultCheckBoxViewFactory extends AbstractPluginHelper implements ViewFactory<CheckBoxView> {

        private static DefaultCheckBoxViewFactory instance;

        private DefaultCheckBoxViewFactory() {
        }

        public static DefaultCheckBoxViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultCheckBoxViewFactory();
            return instance;
        }

        @Override
        public CheckBoxView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = MaterialUi.getUi().checkbox;
            return new CheckBoxViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return CheckBoxViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return CheckBoxViewImpl.class;
        }
    }

    public static class DefaultSimpleCheckBoxViewFactory extends AbstractPluginHelper implements ViewFactory<CheckBoxView> {


        private static DefaultSimpleCheckBoxViewFactory instance;

        private DefaultSimpleCheckBoxViewFactory() {
        }

        public static DefaultSimpleCheckBoxViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultSimpleCheckBoxViewFactory();
            return instance;
        }

        @Override
        public CheckBoxView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = MaterialUi.getUi().simplecheckbox;
            return new CheckBoxViewImplSimple(templateElement);
        }

        @Override
        public String getId() {
            return CheckBoxViewImplSimple.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return CheckBoxViewImplSimple.class;
        }
    }

}