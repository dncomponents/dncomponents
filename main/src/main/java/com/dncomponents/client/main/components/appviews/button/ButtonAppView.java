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

package com.dncomponents.client.main.components.appviews.button;

import com.dncomponents.UiField;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.MouseEnterHandler;
import com.dncomponents.client.dom.handlers.MouseLeaveHandler;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.MouseEvent;

public class ButtonAppView implements IsElement {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement javaTestPanel;
    private static ButtonAppView instance;
    @UiField
    public Button eventButton;
    @UiField
    public TextArea eventsTa;
    @UiField
    public Button<Fruit> rendererButton;

    {
        HtmlBinder.create(ButtonAppView.class, this).bind();
    }

    void test() {
        eventButton.addClickHandler(mouseEvent -> eventsTa.setValue("mouse click"));
        eventButton.addHandler((MouseEnterHandler) mouseEvent -> eventsTa.setValue("mouse enter"));
        eventButton.addHandler((MouseLeaveHandler) mouseEvent -> eventsTa.setValue("mouse leave"));

    }

    public ButtonAppView() {
        initEvents();
        initRenderer();
    }

    private void initRenderer() {
        rendererButton.setRenderer((fruit, slots) ->
                slots.getMainSlot().innerHTML = "<b>" + fruit.getName() + "</b>");
        rendererButton.setUserObject(new Fruit("banana", "sweet fruit"));
    }

    private void initEvents() {
        eventButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                eventsTa.setValue("mouse click");
            }
        });
        eventButton.addHandler(new MouseEnterHandler() {
            @Override
            public void onMouseEnter(MouseEvent mouseEvent) {
                eventsTa.setValue("mouse enter");
            }
        });
        eventButton.addHandler(new MouseLeaveHandler() {
            @Override
            public void onMouseLeave(MouseEvent mouseEvent) {
                eventsTa.setValue("mouse leave");
            }
        });
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }


    public static ButtonAppView getInstance() {
        if (instance == null) instance = new ButtonAppView();
        return instance;
    }
}
