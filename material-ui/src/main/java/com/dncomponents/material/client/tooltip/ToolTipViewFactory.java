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

package com.dncomponents.material.client.tooltip;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tooltip.TooltipView;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;


public class ToolTipViewFactory {


    public static class DefaultToolTipViewFactory extends AbstractPluginHelper implements ViewFactory<TooltipView> {

        private static DefaultToolTipViewFactory instance;

        private DefaultToolTipViewFactory() {
        }

        public static DefaultToolTipViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultToolTipViewFactory();
            return instance;
        }

        @Override
        public TooltipView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).tooltip;
            return new TooltipViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return TooltipViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TooltipViewImpl.class;
        }
    }

}
