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

package com.dncomponents.client.main.components.appviews.tab;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.entities.ItemIdTitle;
import com.dncomponents.client.components.tab.Tab;
import com.dncomponents.client.components.tab.TabItem;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;


public class TabsAppView implements IsElement {
    private static TabsAppView instance;

    @UiField
    HTMLElement root;
    @UiField
    Tab<ItemIdTitle> tabEvents;
    @UiField
    Tab<Fruit> tabRenderer;
    @UiField
    TextArea ta;
    @UiField
    public Tab<ItemIdTitle> tab;
    @UiField
    public TextArea ta2;

    {
        HtmlBinder.create(TabsAppView.class, this).bind();
    }

    public TabsAppView() {
        tabEvents.addBeforeSelectionHandler(event ->
                ta.append("BeforeSelectionEvent - tab: " + event.getItem().getOrder() + "\n"));
        tabEvents.addSelectionHandler(event ->
                ta.append("SelectionEvent - tab: " + event.getSelectedItem().getOrder() + "\n"));
        tabRenderer.setTabItemRenderer((userObject, slots) -> {
            slots.getTitle().textContent = userObject.getName();
            slots.getContent().innerHTML = "<h4>" + DomUtil.escapeHtml(userObject.getDescription()) + "</h4>";
        });
        tab.addSelectionHandler(event ->
                ta2.append("SelectionEvent - tab: " + event.getSelectedItem().getOrder() + "\n"));
        for (Fruit fruit : Fruit.getFruits(5))
            tabRenderer.addItem(new TabItem<>(tabRenderer, fruit));
    }


    @Override
    public HTMLElement asElement() {
        return root;
    }


    public static TabsAppView getInstance() {
        if (instance == null)
            instance = new TabsAppView();
        return instance;
    }
}
