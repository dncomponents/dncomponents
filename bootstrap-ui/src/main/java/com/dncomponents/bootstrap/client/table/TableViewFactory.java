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

package com.dncomponents.bootstrap.client.table;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.bootstrap.client.radio.RadioViewImpl;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.table.TableUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class TableViewFactory {


    public static class DefaultTableViewFactory extends AbstractPluginHelper implements ViewFactory<TableUi> {

        private static DefaultTableViewFactory instance;

        private DefaultTableViewFactory() {
        }

        public static DefaultTableViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultTableViewFactory();
            return instance;
        }

        @Override
        public TableUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (BootstrapUi.getUi()).tableUi;
            return new TableUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return TableUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TableUiImpl.class;
        }
    }

}
