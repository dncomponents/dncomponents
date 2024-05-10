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

package com.dncomponents.bootstrap.client.textarea;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;


public class TextAreaViewFactory {


    public static class DefaultTextAreaViewFactory extends AbstractPluginHelper implements ViewFactory<TextBoxView> {

        private static DefaultTextAreaViewFactory instance;

        private DefaultTextAreaViewFactory() {
        }

        public static DefaultTextAreaViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultTextAreaViewFactory();
            return instance;
        }

        @Override
        public TextBoxView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (BootstrapUi.getUi()).textarea;
            return new TextAreaViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return TextAreaViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TextAreaViewImpl.class;
        }
    }

}
