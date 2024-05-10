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

package com.dncomponents.client.main.components.appviews.tooltip;

import com.dncomponents.UiField;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.open.OpenEvent;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.components.tooltip.BaseTooltip;
import com.dncomponents.client.components.tooltip.Tooltip;
import com.dncomponents.client.main.testing.Fruit;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

public class TooltipAppView implements IsElement {
    @UiField
    HTMLElement root;
    @UiField
    HTMLElement demoPanel;
    @UiField
    Button leftBtn;
    @UiField
    Button rightBtn;
    @UiField
    Button topBtn;
    @UiField
    Button bottomBtn;
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

    private Tooltip.TooltipRenderer<Fruit> renderer = (fruit, slots) -> {
        slots.getContent().innerHTML = "<b>" + fruit.getName() + "</b><p>" + fruit.getDescription() + "</p>";
    };


    public TooltipAppView() {
        HtmlBinder.create(TooltipAppView.class, this).bind();
        initTrigger();
        initRenderer();
        initEvents();
//        initJava();
    }

    private void initEvents() {
        Tooltip<Fruit> tooltip = new Tooltip<>(eventBtn.asElement(), BaseTooltip.Orientation.RIGHT);
        tooltip.setRenderer(renderer);
        tooltip.setUserObject(new Fruit("Apple", "Sweet fruit!"));
        tooltip.addOpenHandler((OpenEvent<Tooltip<Fruit>> event) -> eventsTa.append("open: " + event.getOwner().getUserObject().getName() + "\n"));
        tooltip.addCloseHandler(event -> eventsTa.append("close: " + event.getOwner().getUserObject().getDescription() + "\n"));
    }

    private void initRenderer() {
        Tooltip<Fruit> tooltip = new Tooltip<>(rendererBtn.asElement(), BaseTooltip.Orientation.RIGHT);
        tooltip.setRenderer(renderer);
        tooltip.setUserObject(new Fruit("Apple", "Sweet fruit!"));
    }

    private void initTrigger() {
        Tooltip<Fruit> tooltipLeft = new Tooltip(clickBtn.asElement(), BaseTooltip.Orientation.LEFT, BaseTooltip.Trigger.CLICK);
        tooltipLeft.setRenderer(renderer);
        tooltipLeft.setUserObject(Fruit.fruits.get(0));
        Tooltip<Fruit> tooltipRight = new Tooltip(hoverBtn.asElement(), BaseTooltip.Orientation.RIGHT, BaseTooltip.Trigger.HOVER);
        tooltipRight.setRenderer(renderer);
        tooltipRight.setUserObject(Fruit.fruits.get(1));
        Tooltip<Fruit> tooltipTop = new Tooltip(focusBtn.asElement(), BaseTooltip.Orientation.TOP, BaseTooltip.Trigger.FOCUS);
        tooltipTop.setRenderer(renderer);
        tooltipTop.setUserObject(Fruit.fruits.get(2));
        Tooltip<Fruit> tooltipBottom = new Tooltip(hoverFocusBtn.asElement(), BaseTooltip.Orientation.BOTTOM, BaseTooltip.Trigger.HOVER_FOCUS);
        tooltipBottom.setRenderer(renderer);
        tooltipBottom.setUserObject(Fruit.fruits.get(3));
    }


    @Override
    public HTMLElement asElement() {
        return root;
    }

    static TooltipAppView instance;

    public static TooltipAppView getInstance() {
        if (instance == null)
            instance = new TooltipAppView();
        return instance;
    }
}
