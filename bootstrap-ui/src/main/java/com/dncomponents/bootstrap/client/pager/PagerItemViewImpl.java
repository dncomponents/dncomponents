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

package com.dncomponents.bootstrap.client.pager;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.pager.PagerItemView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class PagerItemViewImpl implements PagerItemView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement textPanel;

    HtmlBinder uiBinder = HtmlBinder.get(PagerItemViewImpl.class, this);


    public PagerItemViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setText(String str) {
        textPanel.innerHTML = str;
    }

    @Override
    public void setActive(boolean b) {
        if (b)
            asElement().classList.add("active");
        else
            asElement().classList.remove("active");
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
