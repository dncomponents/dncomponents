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

package com.dncomponents.material.client.button;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.button.ButtonView;
import com.dncomponents.material.client.textbox.MaterialIcons;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

public class ButtonViewFactory {


    public static class DefaultButtonViewFactory extends AbstractPluginHelper implements ViewFactory<ButtonViewImpl> {

        private static final String VIEW_ID = ButtonViewImpl.VIEW_ID;

        private static DefaultButtonViewFactory instance;

        private DefaultButtonViewFactory() {
        }

        public static DefaultButtonViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultButtonViewFactory();
            return instance;
        }

        @Override
        public ButtonViewImpl getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            ButtonType buttonType = ButtonType.lookUp
                    .getValue(attributes.get(ButtonViewImpl.ButtonBuilder.typeId));
            MaterialIcons leadingIcon = MaterialIcons.lookUp.getValue(attributes.get(ButtonViewImpl.ButtonBuilder.iconId));
            String label = attributes.get(ButtonViewImpl.ButtonBuilder.labelId);
            return ButtonViewImpl.ButtonBuilder.get()
                    .type(buttonType)
                    .setLabel(label)
                    .setIcon(leadingIcon)
                    .build();
        }

        @Override
        public String getId() {
            return VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return ButtonView.class;
        }
    }

}