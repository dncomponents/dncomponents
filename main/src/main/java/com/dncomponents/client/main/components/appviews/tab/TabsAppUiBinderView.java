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

import com.dncomponents.Component;
import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.tab.helper.Orientation;
import com.dncomponents.bootstrap.client.tab.helper.TabType;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.tab.Tab;
import com.dncomponents.client.components.tab.TabItem;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.tab.TabItemViewSlots;
import elemental2.dom.HTMLElement;

import static com.dncomponents.bootstrap.client.tab.TabUiImpl.Builder.get;


//language=html
@Component(template = "<div ui-field=\"root\">\n" +
                      "    <div class=\"row\">\n" +
                      "        <div class=\"col\">\n" +
                      "            <h2>HtmlBinder tabs</h2>\n" +
                      "            <dn-tab type='PILL' orientation='HORIZONTAL' ui-field='f'>\n" +
                      "                <item>\n" +
                      "                    <title>\n" +
                      "                        some title\n" +
                      "                    </title>\n" +
                      "                    <content>\n" +
                      "                        content\n" +
                      "                    </content>\n" +
                      "                </item>\n" +
                      "                <item>\n" +
                      "                    <title>\n" +
                      "                        some title 2\n" +
                      "                    </title>\n" +
                      "                    <content>\n" +
                      "                        content 2\n" +
                      "                    </content>\n" +
                      "                </item>\n" +
                      "            </dn-tab>\n" +
                      "            <dn-tab type='PILL' orientation='VERTICAL' ui-field='f'>\n" +
                      "                <item>\n" +
                      "                    <title>\n" +
                      "                        some title\n" +
                      "                    </title>\n" +
                      "                    <content>\n" +
                      "                        content\n" +
                      "                    </content>\n" +
                      "                </item>\n" +
                      "                <item>\n" +
                      "                    <title>\n" +
                      "                        some title 2\n" +
                      "                    </title>\n" +
                      "                    <content>\n" +
                      "                        content 2\n" +
                      "                    </content>\n" +
                      "                </item>\n" +
                      "            </dn-tab>\n" +
                      "        </div>\n" +
                      "    </div>\n" +
                      "    <div class=\"row\">\n" +
                      "        <div class=\"col\">\n" +
                      "            <h2>Java tabs</h2>\n" +
                      "            <div ui-field=\"javaPanel\">\n" +
                      "            </div>\n" +
                      "        </div>\n" +
                      "    </div>\n" +
                      "</div>")
public class TabsAppUiBinderView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement javaPanel;

    public TabsAppUiBinderView() {
        HtmlBinder.create(TabsAppUiBinderView.class, this).bind();
        init();
    }

    private void init() {
        Tab<Fruit> tab = new Tab<>(get()
                .orientation(Orientation.VERTICAL)
                .type(TabType.TAB)
                .build());

        tab.setTabItemRenderer(new TabItem.RenderTabItem<Fruit>() {
            @Override
            public void render(Fruit userObject, TabItemViewSlots slots) {
                slots.getTitle().innerHTML = userObject.getName();
                slots.getContent().innerHTML = userObject.getDescription();
            }
        });
        TabItem<Fruit> tabItem = new TabItem<>(tab);
        tabItem.setUserObject(new Fruit("banana", "very tasty banana"));
        tab.addItem(tabItem);
        TabItem<Fruit> tabItem2 = new TabItem<>(tab);
        tabItem2.setUserObject(new Fruit("apple", "great fruit apple"));
        tab.addItem(tabItem2);
        javaPanel.appendChild(tab.asElement());
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
