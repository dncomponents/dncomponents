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

package com.dncomponents.bootstrap.client.button;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

public class ButtonViewFactory {


    public static class DefaultButtonViewFactory extends AbstractPluginHelper implements ViewFactory<ButtonViewImpl> {


        private static DefaultButtonViewFactory instance;

        private DefaultButtonViewFactory() {
            arguments.put(ButtonBuilder.colorTypeId, ButtonColorType.lookUp.toStringList());
            arguments.put(ButtonBuilder.outlineColorTypeId, ButtonOutlineColorType.lookUp.toStringList());
            arguments.put(ButtonBuilder.sizeId, ButtonSize.lookUp.toStringList());
        }

        public static DefaultButtonViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultButtonViewFactory();
            return instance;
        }

        @Override
        public ButtonViewImpl getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            ButtonColorType buttonColorType = ButtonColorType.lookUp
                    .getValue(attributes.get(ButtonBuilder.colorTypeId));
            ButtonOutlineColorType outlineColorType = ButtonOutlineColorType.lookUp
                    .getValue(attributes.get(ButtonBuilder.outlineColorTypeId));
            ButtonSize buttonSize = ButtonSize.lookUp
                    .getValue(attributes.get(ButtonBuilder.sizeId));
            return ButtonBuilder
                    .get()
                    .color(buttonColorType)
                    .outlineColor(outlineColorType)
                    .size(buttonSize)
                    .build();
        }

        @Override
        public String getId() {
            return ButtonViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return ButtonViewImpl.class;
        }
    }

    public static class DefaultButtonIconViewFactory extends AbstractPluginHelper implements ViewFactory<ButtonIconViewImpl> {

        private static DefaultButtonIconViewFactory instance;

        private DefaultButtonIconViewFactory() {
            arguments.put(ButtonIconBuilder.iconTypeId, FontAwesome.lookUp.toStringList());
            arguments.put(ButtonIconBuilder.iconSizeId, FontAwesomeSize.lookUp.toStringList());
            arguments.put(ButtonIconBuilder.colorTypeId, ButtonColorType.lookUp.toStringList());
            arguments.put(ButtonIconBuilder.outlineColorTypeId, ButtonOutlineColorType.lookUp.toStringList());
            arguments.put(ButtonIconBuilder.sizeId, ButtonSize.lookUp.toStringList());
        }

        public static DefaultButtonIconViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultButtonIconViewFactory();
            return instance;
        }

        @Override
        public ButtonIconViewImpl getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            FontAwesome iconType = FontAwesome.lookUp
                    .getValue(attributes.get(ButtonIconBuilder.iconTypeId));
            FontAwesomeSize iconSize = FontAwesomeSize.lookUp
                    .getValue(attributes.get(ButtonIconBuilder.iconSizeId));
            ButtonColorType buttonColorType = ButtonColorType.lookUp
                    .getValue(attributes.get(ButtonIconBuilder.colorTypeId));
            ButtonOutlineColorType outlineColorType = ButtonOutlineColorType.lookUp
                    .getValue(attributes.get(ButtonIconBuilder.outlineColorTypeId));
            ButtonSize buttonSize = ButtonSize.lookUp
                    .getValue(attributes.get(ButtonIconBuilder.sizeId));
            return ButtonIconBuilder
                    .get()
                    .template(templateElement)
                    .iconType(iconType)
                    .color(buttonColorType)
                    .outlineColor(outlineColorType)
                    .iconSize(iconSize)
                    .build();
        }

        @Override
        public String getId() {
            return ButtonIconViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return ButtonIconViewImpl.class;
        }
    }


}
