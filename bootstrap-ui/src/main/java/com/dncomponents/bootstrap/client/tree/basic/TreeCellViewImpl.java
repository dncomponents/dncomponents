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

package com.dncomponents.bootstrap.client.tree.basic;

import com.dncomponents.Template;
import com.dncomponents.UiField;
import com.dncomponents.bootstrap.client.cell.BaseCellViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.tree.BaseTreeCellView;
import com.dncomponents.client.views.core.ui.tree.TriConsumer;
import elemental2.dom.*;
import jsinterop.base.Js;


@Template
public class TreeCellViewImpl extends BaseCellViewImpl implements BaseTreeCellView {

    @UiField
    String activeStyle;
    @UiField
    HTMLElement iconPanel;
    @UiField
    HTMLElement iconParent;
    String icon;
    TriConsumer<HTMLElement, HTMLElement, Boolean> dragged;
    HtmlBinder uiBinder = HtmlBinder.create(TreeCellViewImpl.class, this);

    public TreeCellViewImpl() {
    }

    public TreeCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    static HTMLElement draggedElement;
    boolean inserted;
    boolean isFirstElement;

    public TreeCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        initDnd();
    }


    protected void initDnd() {
        asElement().addEventListener("dragstart", new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                draggedElement = Js.cast(evt.target);
            }
        });

        asElement().addEventListener("dragleave", new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                HTMLElement el = Js.cast(evt.target);
                el.style.set("border", "unset");
            }
        });
        asElement().addEventListener("dragover", new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                DragEvent event = Js.cast(evt);
                HTMLElement el = asElement();
                if (draggedElement.equals(el)) {
                    return;
                }
                final double v = el.getBoundingClientRect().bottom - event.clientY;
                final double vv = event.clientY - el.getBoundingClientRect().top;

                if (v < 10) {
                    el.style.set("border", "unset");
                    el.style.set("border-bottom", "2px solid red");
                    inserted = true;
                } else {
                    el.style.set("border", "2px red dotted");
                    inserted = false;
                }
                if (vv < 10) {
                    // Find the first <li> element with the "draggable" attribute within the parent <ul>
                    Element firstDraggableElement = asElement().parentElement.querySelector("li[draggable]");
                    // Check if the element is the first draggable element
                    boolean isFirstDraggable = el.equals(firstDraggableElement);
                    if (isFirstDraggable) {
                        el.style.set("border", "unset");
                        el.style.set("border-top", "2px solid red");
                        inserted = true;
                        isFirstElement = true;
                    }
                }
                DomGlobal.console.log(el.textContent + "  -  " + v);
                evt.preventDefault();
            }
        });


        asElement().addEventListener("drop", new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                dragged.accept(draggedElement, isFirstElement ? null : Js.cast(evt.target), inserted);
            }
        });
    }

    @Override
    public void onItemDragged(TriConsumer<HTMLElement, HTMLElement, Boolean> dragged) {
        this.dragged = dragged;
        asElement().setAttribute("draggable", true);
    }

    public void setActive(boolean b) {
        if (b) asElement().classList.add(activeStyle);
        else asElement().classList.remove(activeStyle);
    }

    @Override
    public void setShift(int level) {
        double space = level * 10 + 5;
        DomUtil.setPaddingLeft(asElement(), space + "px");
    }

    public <C extends BaseTreeCellView> C setIcon(String icon) {
        this.icon = icon;
        if (icon != null && iconParent != null && iconPanel != null) {
            DomUtil.setVisible(iconParent, true);
            Ui.get().getIconRenderer().render(iconPanel, icon);
        }
        return (C) this;
    }
}
