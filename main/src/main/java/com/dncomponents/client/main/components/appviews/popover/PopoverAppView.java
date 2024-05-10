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

package com.dncomponents.client.main.components.appviews.popover;

import com.dncomponents.UiField;
import com.dncomponents.client.components.ListData;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.popover.Popover;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.tooltip.BaseTooltip;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

public class PopoverAppView implements IsElement {
    @UiField
    HTMLElement root;
    @UiField
    Button leftBtn;
    @UiField
    Button rightBtn;
    @UiField
    Button topBtn;
    @UiField
    Button bottomBtn;
    @UiField
    ListData<Fruit, String> listt;
    //events
    @UiField
    Button eventBtn;
    @UiField
    TextArea eventsTa;
    //renderer
    @UiField
    Button rendererBtn;
    //trigger
    @UiField
    Button clickBtn;
    @UiField
    Button focusBtn;
    @UiField
    Button hoverBtn;
    @UiField
    Button hoverFocusBtn;
    @UiField
    public Button btnInfo;


    public PopoverAppView() {
        HtmlBinder.create(PopoverAppView.class, this).bind();
        initEvents();
        initRenderer();
        initTrigger();
    }

    Popover.PopoverRenderer<Fruit> renderer = (fruit, slots) -> {
        slots.getTitle().innerHTML = "<b>" + fruit.getName() + "</b>";
        slots.getContent().innerHTML = "<p>" + fruit.getDescription() + "</p>";
    };

    private void initTrigger() {
        Popover<Fruit> popoverLeft = new Popover(clickBtn.asElement(), BaseTooltip.Orientation.LEFT, BaseTooltip.Trigger.CLICK);
        popoverLeft.setRenderer(renderer);
        popoverLeft.setUserObject(Fruit.fruits.get(0));
        Popover<Fruit> popoverRight = new Popover(hoverBtn.asElement(), BaseTooltip.Orientation.RIGHT, BaseTooltip.Trigger.HOVER);
        popoverRight.setRenderer(renderer);
        popoverRight.setUserObject(Fruit.fruits.get(1));
        Popover<Fruit> popoverTop = new Popover(focusBtn.asElement(), BaseTooltip.Orientation.TOP, BaseTooltip.Trigger.FOCUS);
        popoverTop.setRenderer(renderer);
        popoverTop.setUserObject(Fruit.fruits.get(2));
        Popover<Fruit> popoverBottom = new Popover(hoverFocusBtn.asElement(), BaseTooltip.Orientation.BOTTOM, BaseTooltip.Trigger.HOVER_FOCUS);
        popoverBottom.setRenderer(renderer);
        popoverBottom.setUserObject(Fruit.fruits.get(3));
    }


    private void initEvents() {
        Popover<Fruit> popover = new Popover<>(eventBtn.asElement(), BaseTooltip.Orientation.RIGHT);
        popover.setRenderer(renderer);
        popover.setUserObject(new Fruit("Apple", "Sweet fruit!"));
        popover.addOpenHandler(event -> eventsTa.append("open: " + event.getOwner().getUserObject().getName() + "\n"));
        popover.addCloseHandler(event -> eventsTa.append("close: " + event.getOwner().getUserObject().getDescription() + "\n"));
    }

    private void initRenderer() {
        Popover<Fruit> popover = new Popover<>(rendererBtn.asElement(), BaseTooltip.Orientation.RIGHT);
        popover.setRenderer((fruit, slots) -> {
            slots.getTitle().innerHTML = "<b>" + fruit.getName() + "</b>";
            slots.getContent().innerHTML = "<p>" + fruit.getDescription() + "</p>";
        });
        popover.setUserObject(new Fruit("Apple", "Sweet fruit!"));
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    static PopoverAppView instance;

    public static PopoverAppView getInstance() {
        if (instance == null)
            instance = new PopoverAppView();
        return instance;
    }

}
