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

package com.dncomponents.material.client.pager;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.pager.PagerItemView;
import com.dncomponents.client.views.core.ui.pager.PagerUi;
import com.dncomponents.client.views.core.ui.pager.PagerView;
import elemental2.dom.HTMLTemplateElement;

public class PagerUiImpl implements PagerUi {

    @UiField
    HTMLTemplateElement pagerItem;
    @UiField
    HTMLTemplateElement pager;

    HtmlBinder uiBinder = HtmlBinder.create(PagerUiImpl.class, this);


    public PagerUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public PagerItemView getPagerItemView() {
        return new PagerItemViewImpl(pagerItem);
    }

    PagerView pagerView;

    @Override
    public PagerView getRootView() {
        if (pagerView == null)
            pagerView = new PagerViewImpl(pager);
        return pagerView;
    }
}
