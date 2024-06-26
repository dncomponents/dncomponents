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

package com.dncomponents.client.components.tab;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponentSingleSelection;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.entities.ItemIdTitle;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.beforeselection.BeforeSelectionHandler;
import com.dncomponents.client.components.core.events.beforeselection.HasBeforeSelectionHandlers;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.tab.TabUi;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;

import java.util.Collections;
import java.util.Map;

public class Tab<T> extends BaseComponentSingleSelection<T, TabUi, TabItem<T>> implements HasBeforeSelectionHandlers<TabItem<T>> {

    TabItem.RenderTabItem<T> tabItemRenderer;

    public Tab(TabUi ui) {
        super(ui);
    }

    public Tab() {
        this(Ui.get().getTabUi());
    }

    @Override
    public void addItem(TabItem<T> item) {
        super.addItem(item);
        view.getRootView().addItem(item.getTitle(), item.getContent());
        setSelected(getItems().get(0), true);
    }

    @Override
    public TabItem<T> createItem(T t) {
        return new TabItem<>(this, t);
    }

    @Override
    public HandlerRegistration addBeforeSelectionHandler(BeforeSelectionHandler<TabItem<T>> handler) {
        return handler.addTo(asElement());
    }

    @Override
    protected TabUi getView() {
        return super.getView();
    }

    public static class TabHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static String TITLE_TAG = "title";
        private static String ICON_TAG = "icon";
        private static String CONTENT_TAG = "content";


        private static Tab.TabHtmlParser instance;

        private TabHtmlParser() {
            tags.put(TITLE_TAG, Collections.emptyList());
            tags.put(CONTENT_TAG, Collections.emptyList());
        }

        public static Tab.TabHtmlParser getInstance() {
            if (instance == null)
                return instance = new Tab.TabHtmlParser();
            return instance;
        }

        @Override
        public Tab parse(Element htmlElement, Map<String, ?> elements) {
            Tab<ItemIdTitle> tab;
            TabUi tabUi = getView(Tab.class, htmlElement, elements);
            if (tabUi != null)
                tab = new Tab<>(tabUi);
            else
                tab = new Tab<>();
            tab.setTabItemRenderer((accordionIdItem, slots) -> {
                slots.getTitle().innerHTML = accordionIdItem.getTitle();
                slots.getContent().innerHTML = accordionIdItem.getContent();
                Ui.get().getIconRenderer().render(slots.getIcon(), accordionIdItem.getIcon());
            });

            NodeList<Element> elementsByTagName = htmlElement.getElementsByTagName(ITEM);
            for (int i = 0; i < elementsByTagName.length; i++) {
                TabItem<ItemIdTitle> tabItem = parseTabItem((HTMLElement) elementsByTagName.getAt(i), tab);
                tab.addItem(tabItem);
            }
            replaceAndCopy(htmlElement, tab);
            return tab;
        }

        public TabItem<ItemIdTitle> parseTabItem(HTMLElement html, Tab tab) {
            TabItem<ItemIdTitle> item = new TabItem<>(tab);
            ItemIdTitle idItem = new ItemIdTitle();
            idItem.setId(getElementId(html));
            NodeList<Element> titles = html.getElementsByTagName(TITLE_TAG);
            for (int i = 0; i < titles.length; i++) {
                idItem.setTitle(titles.getAt(i).textContent);
                break;
            }
            NodeList<Element> icons = html.getElementsByTagName(ICON_TAG);
            for (int i = 0; i < icons.length; i++) {
                idItem.setIcon(icons.getAt(i).textContent);
                break;
            }
            NodeList<Element> contents = html.getElementsByTagName(CONTENT_TAG);
            for (int i = 0; i < contents.length; i++) {
                idItem.setContent(contents.getAt(i).innerHTML);
                break;
            }
            item.setUserObject(idItem);
            return item;
        }

        @Override
        public String getId() {
            return "dn-tab";
        }

        @Override
        public Class getClazz() {
            return Tab.class;
        }

    }

    public void setTabItemRenderer(TabItem.RenderTabItem<T> tabItemRenderer) {
        this.tabItemRenderer = tabItemRenderer;
    }


}
