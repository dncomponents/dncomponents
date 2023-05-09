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
package com.dncomponents.bootstrap.client.tree.basic;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@Component
public class TreeCellParentViewImpl extends TreeCellViewImpl implements ParentTreeCellView {

    @UiField
    public HTMLElement openCloseElement;

    @UiField
    public String openStyle;
    @UiField
    public String closeStyle;

    HtmlBinder uiBinder = HtmlBinder.create(TreeCellParentViewImpl.class, this);

    public TreeCellParentViewImpl() {
    }

    public TreeCellParentViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TreeCellParentViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        initDnd();
    }

    @Override
    public void setOpened(boolean b) {
        if (!b)
            openCloseElement.className = openStyle;
        else
            openCloseElement.className = closeStyle;
    }

    @Override
    public void addOpenCloseHandler(BaseEventListener handler) {
        handler.addTo(openCloseElement);
    }
}
