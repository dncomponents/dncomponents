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

package com.dncomponents.client.components.core;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.HasHandlers;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.*;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import elemental2.dom.HTMLElement;


/**
 * Base class for all components.
 * View logic is separated in {@link View}.
 * View implementations can be injected through constructor for concrete object
 * and through static methods {@link Ui} on application level.
 */
public abstract class BaseComponent<T, V extends View> implements IsElement, HasUserValue<T>,
        HasHandlers {

    protected final V view;
    protected T userObject;
    protected Renderer renderer;
    protected ViewSlots viewSlots;

    protected ViewSlots getViewSlots() {
        if (view instanceof HasViewSlots)
            viewSlots = ((HasViewSlots) view).getViewSlots();
        return viewSlots;
    }

    public static WeakMap allComponents = new WeakMap();


    public static <C> C getSource(Event event) {
        return (C) allComponents.get(event.target);
    }

    //
    public static <T extends BaseComponent> T source(Event event) {
        return (T) allComponents.get(event.target);
    }

    public BaseComponent(V view) {
        if (view == null)
            throw new NullPointerException("View can't be null! Please check view initialisation.");
        this.view = view;
        HTMLElement viewElement = this.view.asElement();
        if (viewElement == null)
            throw new IllegalStateException("View " + view.getClass() + " root element is null. Please check if you properly bind elements!");
        allComponents.set(viewElement, this);
        if (Ui.isDebug()) {
            viewElement.setAttribute("component-class", this.getClass().getSimpleName());
            viewElement.setAttribute("view-class", this.view.getClass().getSimpleName());
        }
    }

    protected V getView() {
        return view;
    }

    @Override
    public T getUserObject() {
        return userObject;
    }

    @Override
    public void setUserObject(T userObject) {
        this.userObject = userObject;
        render();
    }

    protected void render() {
        if (renderer != null) {
            renderer.render(userObject, getViewSlots());
        }
    }

    public interface DrawOnSlots {
        void apply(ViewSlots slots);
    }

    public void drawOnSlotsBase(DrawOnSlots drawOnSlots) {
        drawOnSlots.apply(getViewSlots());
    }

    @Override
    public HTMLElement asElement() {
        return getView().asElement();
    }


    protected void setRendererBase(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void fireEvent(CustomEvent event) {
        this.asElement().dispatchEvent(event);
    }

    public HandlerRegistration addHandler(BaseEventListener handler) {
        return handler.addTo(asElement());
    }

    public void setStyle(String style) {
        DomUtil.setStyle(asElement(), style);
    }

    public void addStyle(String style) {
        DomUtil.addStyle(asElement(), style);
    }

    public void removeStyle(String style) {
        DomUtil.removeStyle(asElement(), style);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
