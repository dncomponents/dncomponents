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

package com.dncomponents.bootstrap.client.sidemenu;

import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.tree.basic.TreeCellParentViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;


public class SideMenuTreeCellParentViewImpl extends TreeCellParentViewImpl {

    @UiField
    HTMLElement valuePanelParent;

    HtmlBinder uiBinder = HtmlBinder.create(SideMenuTreeCellParentViewImpl.class, this);

    public SideMenuTreeCellParentViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public void addOpenCloseHandler(BaseEventListener handler) {
        handler.addTo(root);
    }

    @Override
    public void setOpened(boolean b) {
        if (!b)
            valuePanelParent.classList.add(closeStyle);
        else
            valuePanelParent.classList.remove(closeStyle);
    }

}
