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

package com.dncomponents.client.components.tooltip;

import com.dncomponents.client.components.core.*;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.close.CloseEvent;
import com.dncomponents.client.components.core.events.close.CloseHandler;
import com.dncomponents.client.components.core.events.close.HasCloseHandlers;
import com.dncomponents.client.components.core.events.open.HasOpenHandlers;
import com.dncomponents.client.components.core.events.open.OpenEvent;
import com.dncomponents.client.components.core.events.open.OpenHandler;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.Renderer;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.tooltip.TooltipView;
import com.dncomponents.client.views.core.ui.tooltip.TooltipViewSlots;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

import java.util.Collections;
import java.util.Map;

/**
 * Created by nikolasavic
 */
public class Tooltip<T> extends BaseTooltip<T, TooltipView> implements HasCloseHandlers<Tooltip<T>>, HasOpenHandlers<Tooltip<T>> {


    public Tooltip() {
        super(Ui.get().getTooltipView());
    }

    public Tooltip(TooltipView view) {
        super(view);
    }

    public Tooltip(HTMLElement owner, Orientation orientation) {
        this(orientation);
        setOwner(owner);
    }

    public Tooltip(HTMLElement owner, Orientation orientation, String content) {
        this(orientation);
        setOwner(owner);
        setContent(content);
    }

    public Tooltip(HTMLElement owner, Orientation orientation, Trigger trigger) {
        this(orientation);
        setOwner(owner);
        setTrigger(trigger);
    }

    public Tooltip(HTMLElement owner, Orientation orientation, Trigger trigger, String content) {
        this(orientation);
        setOwner(owner);
        setTrigger(trigger);
        setContent(content);
    }

    public Tooltip(BaseTooltip.Orientation orientation) {
        this();
        setOrientation(orientation);
    }

    public interface TooltipRenderer<T> extends Renderer<T, TooltipViewSlots> {
    }

    public void setRenderer(TooltipRenderer<T> renderer) {
        super.setRendererBase(renderer);
    }

    @Override
    protected void fireShowEvent() {
        OpenEvent.fire(this, this);
    }

    @Override
    protected void fireCloseEvent() {
        CloseEvent.fire(this, this);
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler<Tooltip<T>> handler) {
        return addHandler(handler);
    }

    @Override
    public HandlerRegistration addOpenHandler(OpenHandler<Tooltip<T>> handler) {
        return addHandler(handler);
    }

    @Override
    protected TooltipViewSlots getViewSlots() {
        return (TooltipViewSlots) super.getViewSlots();
    }

    public static class TooltipHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        public static final String ORIENTATION = "orientation";
        public static final String TRIGGER = "trigger";
        public static final String OWNER = "owner";

        private static TooltipHtmlParser instance;

        private TooltipHtmlParser() {
            arguments.put(OWNER, Collections.emptyList());
            arguments.put(ORIENTATION, Orientation.lookUp.toStringList());
            arguments.put(TRIGGER, Trigger.lookUp.toStringList());
        }

        public static TooltipHtmlParser getInstance() {
            if (instance == null)
                return instance = new TooltipHtmlParser();
            return instance;
        }

        @Override
        public Tooltip parse(Element html, Map<String, ?> templateElement) {
            Orientation orientation = null;
            if (html.hasAttribute(ORIENTATION)) {
                String or = html.getAttribute(ORIENTATION);
                Orientation orValue = Orientation.lookUp.getValue(or);
                if (orValue != null)
                    orientation = orValue;
            }
            Tooltip tooltip = new Tooltip(orientation);
            tooltip.getViewSlots().getContent().innerHTML = html.innerHTML;

            DomUtil.copyAllAttributes(html, tooltip.asElement());
            HTMLElement toReplace = DomUtil.createDiv("<dn-tooltip-2></dn-tooltip-2>"); //todo remove this
            DomUtil.copyAllAttributes(html, toReplace);
            toReplace.setAttribute("pp", toReplace.getAttribute("ui-field"));
            toReplace.removeAttribute("ui-field");
            html.parentNode.appendChild(toReplace);
            DomUtil.replace(tooltip.asElement(), html);
            return tooltip;
        }

        @Override
        public String getId() {
            return "dn-tooltip";
        }

        @Override
        public Class getClazz() {
            return Tooltip.class;
        }

    }

    public static class TooltipAfterHtmlParser extends AbstractPluginHelper implements HtmlParser<String> {

        private static TooltipAfterHtmlParser instance;

        public static final String TRIGGER = "trigger";

        private TooltipAfterHtmlParser() {
        }

        public static TooltipAfterHtmlParser getInstance() {
            if (instance == null)
                return instance = new TooltipAfterHtmlParser();
            return instance;
        }

        @Override
        public String parse(Element htmlElement1, Map<String, ?> template) {
            HTMLElement htmlElement = (HTMLElement) htmlElement1.parentNode;
            if (htmlElement.hasAttribute("owner")) {
                String owner = htmlElement.getAttribute("owner");
                Object ownerObj = template.get(owner);
                String uiField = htmlElement.getAttribute("pp");
                Tooltip tooltip = (Tooltip) template.get(uiField);
                tooltip.asElement().remove();
                if (ownerObj instanceof IsElement) {
                    tooltip.setOwner(((IsElement) ownerObj).asElement());
                }
                if (ownerObj instanceof HTMLElement) {
                    tooltip.setOwner((HTMLElement) ownerObj);
                }
                if (htmlElement.hasAttribute(TRIGGER)) {
                    String or = htmlElement.getAttribute(TRIGGER);
                    Trigger orValue = Trigger.lookUp.getValue(or);
                    if (orValue != null)
                        tooltip.setTrigger(orValue);
                }
            }
            htmlElement.remove();
            return null;
        }

        @Override
        public String getId() {
            return "dn-tooltip-2";
        }

        @Override
        public Class getClazz() {
            return Tooltip.class;
        }
    }

}
