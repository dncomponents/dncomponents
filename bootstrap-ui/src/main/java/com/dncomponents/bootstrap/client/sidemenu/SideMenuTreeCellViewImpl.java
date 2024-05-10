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

import com.dncomponents.Template;
import com.dncomponents.bootstrap.client.tree.basic.TreeCellViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.MouseEvent;


@Template
public class SideMenuTreeCellViewImpl extends TreeCellViewImpl {


    HtmlBinder uiBinder = HtmlBinder.create(SideMenuTreeCellViewImpl.class, this);

    public SideMenuTreeCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        DomUtil.addHandler(valuePanel, new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                mouseEvent.preventDefault();
            }
        });
    }

    @Override
    public void addClickHandler(ClickHandler clickHandler) {
        clickHandler.addTo(valuePanel);
    }

}
