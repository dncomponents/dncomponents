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

package com.dncomponents.client.components.multi;

import com.dncomponents.client.components.core.events.value.TakesValue;
import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.Renderer;
import com.dncomponents.client.views.ViewSlots;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;


public abstract class BaseHasView<T, V extends View> implements TakesValue<T>, IsElement {

    protected Renderer renderer;

    protected T value;

    protected final V view;

    protected ViewSlots viewSlots;


    public BaseHasView(V view) {
        this.view = view;
    }

    @Override
    public HTMLElement asElement() {
        return view.asElement();
    }

    protected V getView() {
        return view;
    }


    protected void setRendererBase(Renderer renderer) {
        this.renderer = renderer;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        render();
    }

    protected void render() {
        if (renderer != null) {
            renderer.render(value, getViewSlots());
        }
    }

    protected ViewSlots getViewSlots() {
        if (view instanceof HasViewSlots)
            viewSlots = ((HasViewSlots) view).getViewSlots();
        return viewSlots;
    }

}
