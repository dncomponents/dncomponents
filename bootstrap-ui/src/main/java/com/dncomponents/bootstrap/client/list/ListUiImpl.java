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

package com.dncomponents.bootstrap.client.list;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.dncomponents.client.views.core.ui.list.ListCellCheckBoxView;
import com.dncomponents.client.views.core.ui.list.ListUi;
import com.dncomponents.client.views.core.ui.list.ListView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class ListUiImpl implements ListUi {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLTemplateElement listMain;

    @UiField
    public HTMLTemplateElement listItem;

    @UiField
    HTMLTemplateElement listItemCheckbox;

    protected HtmlBinder uiBinder = HtmlBinder.get(ListUiImpl.class, this);

    ListView listView;

    public ListUiImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public ListUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public BaseCellView getListCellView() {
        return new ListCellViewImpl(listItem);
    }

    @Override
    public ListCellCheckBoxView getListCheckBoxView() {
        return new ListCellCheckBoxViewImpl(listItemCheckbox);
    }

    @Override
    public ListView getRootView() {
        if (listView == null)
            listView = new ListViewImpl(listMain);
        return listView;
    }
}
