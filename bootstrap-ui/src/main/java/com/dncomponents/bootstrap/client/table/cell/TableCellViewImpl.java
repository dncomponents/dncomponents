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

package com.dncomponents.bootstrap.client.table.cell;

import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.cell.BaseCellViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TableCellViewImpl extends BaseCellViewImpl {
    public TableCellViewImpl() {
    }

    HtmlBinder uiBinder = HtmlBinder.get(TableCellViewImpl.class, this);

    public TableCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }


    public TableCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    private void init() {
        root.setAttribute("tabindex", 0);
    }

    @Override
    public HTMLElement getValuePanel() {
        return asElement();
    }
}
