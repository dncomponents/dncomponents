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

package com.dncomponents.client.components.accordion;

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.CanSelect;
import com.dncomponents.client.components.core.events.beforeselection.BeforeSelectionEvent;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.components.modal.SetElement;
import com.dncomponents.client.views.Renderer;
import com.dncomponents.client.views.core.ui.accordion.AccordionItemView;
import com.dncomponents.client.views.core.ui.accordion.AccordionItemViewSlots;
import elemental2.dom.Event;
import elemental2.dom.EventListener;


public class AccordionItem<T> extends BaseComponent<T, AccordionItemView> implements CanSelect {

    final Accordion<T> accordion;
    boolean selected;

    public AccordionItem(Accordion accordion) {
        super(accordion.getView().getAccordionItemView());
        this.accordion = accordion;
        setRenderer(accordion.accordionItemRenderer);
        bind();
    }

    public AccordionItem(Accordion accordion, AccordionItemView view) {
        super(view);
        this.accordion = accordion;
        setRenderer(accordion.accordionItemRenderer);
        bind();
    }

    public AccordionItem(Accordion accordion, String titleHtml, String contentHtml) {
        this(accordion, accordion.getView().getAccordionItemView());
        setTitle(titleHtml);
        setContent(contentHtml);
    }

    public AccordionItem(Accordion accordion, T userObject) {
        this(accordion, accordion.getView().getAccordionItemView());
        setUserObject(userObject);
    }

    public AccordionItem(Accordion accordion, T userObject, RenderAccordionItem<T> renderer) {
        this(accordion, accordion.getView().getAccordionItemView());
        setRenderer(renderer);
        setUserObject(userObject);
    }

    private void bind() {
        view.addItemSelectedHandler(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                onItemSelected();
            }
        });
    }

    void onItemSelected() {
        BeforeSelectionEvent.fire(accordion, this);
        accordion.setSelected(AccordionItem.this, !AccordionItem.this.isSelected(), true);
        SelectionEvent.fire(accordion, this);
    }

    @Override
    protected AccordionItemView getView() {
        return super.getView();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean b) {
        selected = b;
        view.select(b);
    }

    public String getTitle() {
        return view.getTitle();
    }

    public String getContent() {
        return view.getContent();
    }

    public void setTitle(String html) {
        view.setItemTitle(html);
    }

    public void setTitle(SetElement se) {
        se.setHtml(getViewSlots().getTitle());
    }

    public void setContent(String html) {
        view.setItemContent(html);
    }

    public void setContent(SetElement se) {
        se.setHtml(getViewSlots().getContent());
    }

    public interface RenderAccordionItem<T> extends Renderer<T, AccordionItemViewSlots> {
    }

    public void setRenderer(RenderAccordionItem<T> renderer) {
        super.setRendererBase(renderer);
    }

    @Override
    public AccordionItemViewSlots getViewSlots() {
        return (AccordionItemViewSlots) super.getViewSlots();
    }
}
