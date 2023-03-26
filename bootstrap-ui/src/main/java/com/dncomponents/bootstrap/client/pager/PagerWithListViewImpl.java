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
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.pager.PagerWithListView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

import java.util.ArrayList;

/**
 * @author nikolasavic
 */
public class PagerWithListViewImpl implements PagerWithListView {

    @UiField
    HTMLElement root;

    Presenter presenter;


    HtmlBinder uiBinder = HtmlBinder.get(PagerWithListViewImpl.class, this);


    public PagerWithListViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setText(String s) {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setPageNumber(int pageNumber) {

    }

    @Override
    public void enablePrevious(boolean b) {

    }

    @Override
    public void enableNext(boolean b) {

    }

    @Override
    public void setNumberOfPages(int numberOfPages) {

    }

    @Override
    public void update(ArrayList<Integer> itemsList) {

    }

    @Override
    public void addItem(IsElement element) {
        asElement().appendChild(element.asElement());
    }

    @Override
    public void clear() {
        asElement().innerHTML = "";
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
