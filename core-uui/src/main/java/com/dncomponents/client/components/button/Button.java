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

package com.dncomponents.client.components.button;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.click.HasClickHandlers;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.button.ButtonView;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.Node;
import elemental2.dom.NodeList;

import java.util.Collections;
import java.util.Map;

/**
 * @author nikolasavic
 */
public class Button<T> extends BaseComponent<T, ButtonView> implements HasClickHandlers {

    private boolean enabled;

    public Button() {
        this(Ui.get().getButtonView());
    }

    public Button(String text) {
        this();
        setText(text);
        setRenderer(new MainRenderer<T>() {
            @Override
            public void render(T t, MainViewSlots slots) {
                slots.getMainSlot().innerHTML = t + "";
            }
        });
    }

    public Button(String text, ClickHandler clickHandler) {
        this();
        setText(text);
        addClickHandler(clickHandler);
    }

    public Button(ButtonView view, String text) {
        super(view);
        setText(text);
    }

    public Button(ButtonView view) {
        super(view);
    }


    public void setText(String text) {
        view.setText(text);
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return handler.addTo(asElement());
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        view.setEnabled(this.enabled);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setRenderer(MainRenderer<T> renderer) {
        super.setRendererBase(renderer);
    }

    @Override
    protected MainViewSlots getViewSlots() {
        return (MainViewSlots) super.getViewSlots();
    }

    public static class ButtonHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        public static final String DISABLED = "disabled";

        private static ButtonHtmlParser instance;

        private ButtonHtmlParser() {
            arguments.put(DISABLED, Collections.emptyList());
        }

        public static ButtonHtmlParser getInstance() {
            if (instance == null)
                return instance = new ButtonHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            Button button;
            ButtonView view = getView(Button.class, htmlElement, elements);
            if (view != null)
                button = new Button(view);
            else
                button = new Button();

            button.setEnabled(!htmlElement.hasAttribute(DISABLED));
            button.getViewSlots().getMainSlot().innerHTML = htmlElement.innerHTML;
//            for (Node node : htmlElement.childNodes.asList()) {
//                button.getViewSlots().getMainSlot().appendChild(node);
//                DomGlobal.console.log(node.nodeName);
//            }
            replaceAndCopy(htmlElement, button);
            return button;
        }

        @Override
        public String getId() {
            return "dn-button";
        }

        @Override
        public Class getClazz() {
            return Button.class;
        }
    }

}
