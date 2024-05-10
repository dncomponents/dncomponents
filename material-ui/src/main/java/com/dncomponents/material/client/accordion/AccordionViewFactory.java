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

package com.dncomponents.material.client.accordion;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.accordion.AccordionUi;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;


public class AccordionViewFactory {


    public static class DefaultAccordionViewFactory extends AbstractPluginHelper implements ViewFactory<AccordionUi> {

        private static DefaultAccordionViewFactory instance;

        private DefaultAccordionViewFactory() {
        }

        public static DefaultAccordionViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultAccordionViewFactory();
            return instance;
        }

        @Override
        public AccordionUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).accordionUi;
            return new AccordionUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return AccordionUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return AccordionUiImpl.class;
        }
    }

}