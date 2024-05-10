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

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.CanSelect;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.beforeselection.BeforeSelectionEvent;
import com.dncomponents.client.components.core.events.beforeselection.BeforeSelectionHandler;
import com.dncomponents.client.components.core.events.beforeselection.HasBeforeSelectionHandlers;
import com.dncomponents.client.components.modal.SetElement;
import com.dncomponents.client.views.Renderer;
import com.dncomponents.client.views.core.ui.tab.TabItemView;
import com.dncomponents.client.views.core.ui.tab.TabItemViewSlots;
import elemental2.dom.HTMLElement;


public class TabItem<T> extends BaseComponent<T, TabItemView> implements CanSelect, HasBeforeSelectionHandlers {

    final Tab<T> tab;
    boolean selected;

    {
        setRenderer((t, slots) ->
                slots.getContent().textContent = t + "");
    }

    public TabItem(Tab tab) {
        super(tab.getView().getTabItemView());
        this.tab = tab;
        setRenderer(tab.tabItemRenderer);
        bind();
    }

    public TabItem(Tab tab, TabItemView view) {
        super(view);
        this.tab = tab;
        setRenderer(tab.tabItemRenderer);
        bind();
    }

    public TabItem(Tab tab, String titleHtml, String contentHtml) {
        this(tab, tab.getView().getTabItemView());
        setTitle(titleHtml);
        setContent(contentHtml);
    }

    public TabItem(Tab tab, T userObject) {
        this(tab, tab.getView().getTabItemView());
        setUserObject(userObject);
    }

    public TabItem(Tab tab, T userObject, RenderTabItem<T> renderer) {
        this(tab, tab.getView().getTabItemView());
        tab.addItem(this);
        setRenderer(renderer);
        setUserObject(userObject);
    }

    private void bind() {
        view.addItemSelectedHandler(evt -> {
            BeforeSelectionEvent.fire(tab, TabItem.this);
            tab.setSelected(TabItem.this, !TabItem.this.isSelected(), true);
        });
    }

    HTMLElement getTitle() {
        return view.getTabItemNav();
    }

    HTMLElement getContent() {
        return view.getTabItemContent();
    }

    @Override
    protected TabItemView getView() {
        return super.getView();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean b) {
        selected = b;
        view.select(b);
    }

    public void setTitle(String html) {
        view.setItemTitleHtml(html);
    }

    public void setTitle(SetElement se) {
        se.setHtml(getViewSlots().getTitle());
    }

    public void setContent(String html) {
        view.setItemContent(html);
    }

    public void setContent(HTMLElement content) {
        view.setItemContent(content);
    }

    public void setContent(SetElement se) {
        se.setHtml(getViewSlots().getContent());
    }

    public int getOrder() {
        return tab.getItems().indexOf(this);
    }

    @Override
    public HandlerRegistration addBeforeSelectionHandler(BeforeSelectionHandler handler) {
        return handler.addTo(asElement());
    }

    public interface RenderTabItem<T> extends Renderer<T, TabItemViewSlots> {
    }

    public void setRenderer(RenderTabItem<T> renderer) {
        super.setRendererBase(renderer);
    }

    @Override
    public TabItemViewSlots getViewSlots() {
        return (TabItemViewSlots) super.getViewSlots();
    }
}
