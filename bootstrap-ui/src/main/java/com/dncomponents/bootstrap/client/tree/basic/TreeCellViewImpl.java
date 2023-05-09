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

package com.dncomponents.bootstrap.client.tree.basic;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.bootstrap.client.cell.BaseCellViewImpl;
import com.dncomponents.client.views.core.ui.tree.TriConsumer;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.tree.BaseTreeCellView;
import elemental2.dom.*;
import jsinterop.base.Js;

/**
 * @author nikolasavic
 */
@Component
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
                HTMLElement el = Js.cast(evt.target);
                final double v = el.getBoundingClientRect().bottom - event.clientY;
                if (v < 10) {
                    el.style.set("border", "unset");
                    el.style.set("border-bottom", "2px solid red");
                    inserted = true;
                } else {
                    el.style.set("border", "2px red dotted");
                    inserted = false;
                }
                DomGlobal.console.log(el.textContent + "  -  " + v);
                evt.preventDefault();
            }
        });


        asElement().addEventListener("drop", new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                dragged.accept(draggedElement, Js.cast(evt.target), inserted);
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
    public void setPadding(double padding) {
        DomUtil.setPaddingLeft(asElement(), padding + "px");
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
