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

package com.dncomponents.bootstrap.client.dropdown;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.bootstrap.client.accordion.AccordionUiImpl;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.accordion.AccordionUi;
import com.dncomponents.client.views.core.ui.dropdown.DropDownUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class DropdownViewFactory {


    public static class DefaultDropdownViewFactory extends AbstractPluginHelper implements ViewFactory<DropDownUi> {

        private static DefaultDropdownViewFactory instance;

        private DefaultDropdownViewFactory() {
        }

        public static DefaultDropdownViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultDropdownViewFactory();
            return instance;
        }

        @Override
        public DropDownUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (BootstrapUi.getUi()).dropDownUi;
            return new DropDownUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return DropDownUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return DropDownUiImpl.class;
        }
    }

}
