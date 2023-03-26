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

package com.dncomponents.bootstrap.client.progress;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.progress.ProgressView;
import elemental2.dom.HTMLTemplateElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nikolasavic
 */
public class ProgressViewFactory {
    public static class DefaultProgressViewFactory extends AbstractPluginHelper implements ViewFactory<ProgressView> {

        private static DefaultProgressViewFactory instance;

        private DefaultProgressViewFactory() {
            arguments.put(ProgressViewImpl.Builder.typeId, ProgressBarTypes.lookUp.toStringList());
            arguments.put(ProgressViewImpl.Builder.colorTypeId, ProgressBarColorTypes.lookUp.toStringList());
        }

        public static DefaultProgressViewFactory getInstance() {
            if (instance == null)
                return instance = new DefaultProgressViewFactory();
            return instance;
        }

        @Override
        public ProgressView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            String typesStringList = attributes.get(ProgressViewImpl.Builder.typeId);
            List<ProgressBarTypes> types = new ArrayList<>();
            if (typesStringList != null) {
                String res[] = typesStringList.split("\\s+");
                for (String re : res) {
                    types.add(ProgressBarTypes.lookUp.getValue(re));
                }
            }
            ProgressBarColorTypes color = ProgressBarColorTypes.lookUp.getValue(attributes.get(ProgressViewImpl.Builder.colorTypeId));
            ProgressViewImpl progressView = ProgressViewImpl.Builder.get().type(types).color(color).build();
            return progressView;
        }

        @Override
        public String getId() {
            return ProgressViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return ProgressViewImpl.class;
        }
    }
}
