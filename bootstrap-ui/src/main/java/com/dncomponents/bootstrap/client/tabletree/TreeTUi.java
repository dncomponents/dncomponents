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

package com.dncomponents.bootstrap.client.tabletree;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.table.group.ParentTableTreeCellViewImpl;
import com.dncomponents.bootstrap.client.tree.TreeUiImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TreeTUi extends TreeUiImpl {


    HtmlBinder binder = HtmlBinder.get(TreeTUi.class, this);
    @UiField
    HTMLTemplateElement tableTreeItemSimpleParent;

    public TreeTUi() {
        binder.bind();
    }

    public TreeTUi(String template) {
        super(template);
    }

    @Override
    public ParentTreeCellView getParentTreeCellView(String icon) {
        return new ParentTableTreeCellViewImpl(tableTreeItemSimpleParent).setIcon(icon);
    }
}
