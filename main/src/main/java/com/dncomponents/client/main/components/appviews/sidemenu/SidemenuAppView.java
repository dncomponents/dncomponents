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

package com.dncomponents.client.main.components.appviews.sidemenu;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.components.sidemenu.SideMenu;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

public class SidemenuAppView implements IsElement {
    static SidemenuAppView instance;

    @UiField
    HTMLElement root;
    @UiField
    SideMenu<ItemId> sideMenu;
    @UiField
    TextArea ta;
    @UiField(provided = true)
    public SideMenu<Fruit> eventsSideMenu = new SideMenu<>();
    @UiField
    public TextArea taE;
    @UiField(provided = true)
    public SideMenu<Fruit> rendererSideMenu = new SideMenu<>();

    public SidemenuAppView() {
        initRenderer();
        HtmlBinder.create(SidemenuAppView.class, this).bind();
        initEvents();
        init();
    }

    private void init() {
        sideMenu.addSelectionHandler(event ->
                ta.setValue("Selected: id=" + event.getSelectedItem().getId() + "  " + event.getSelectedItem().getContent().trim()));
    }

    private void initRenderer() {
        rendererSideMenu.setRootMenuItem(Fruit.getFruitsTree());
        rendererSideMenu.setItemRenderer(r ->
                r.valuePanel.innerHTML = "*");
        rendererSideMenu.draw();
    }

    private void initEvents() {
        eventsSideMenu.setRootMenuItem(Fruit.getFruitsTree());
        eventsSideMenu.addSelectionHandler(event -> taE.append(event.getSelectedItem() + "\n"));
        eventsSideMenu.draw();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }


    public static SidemenuAppView getInstance() {
        if (instance == null)
            instance = new SidemenuAppView();
        return instance;
    }

}
