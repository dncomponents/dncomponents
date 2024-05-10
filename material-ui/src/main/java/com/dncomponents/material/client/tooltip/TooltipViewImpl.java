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

package com.dncomponents.material.client.tooltip;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.tooltip.TooltipView;
import com.dncomponents.client.views.core.ui.tooltip.TooltipViewSlots;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;


public class TooltipViewImpl<V extends TooltipViewSlots> implements TooltipView<V> {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLElement root;
    @UiField
    protected HTMLElement contentPanel;
    @UiField
    String topStyle;
    @UiField
    String bottomStyle;
    @UiField
    String leftStyle;
    @UiField
    String rightStyle;
    @UiField
    String baseStyle;
    @UiField
    String fadeStyle;
    @UiField
    String showStyle;

    HtmlBinder uiBinder = HtmlBinder.create(TooltipViewImpl.class, this);

    public TooltipViewImpl() {
    }

    public TooltipViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    public void setBottomOrientation() {
        asElement().className = (baseStyle + " " + fadeStyle + " " + bottomStyle + " " + showStyle);
    }

    @Override
    public void setTopOrientation() {
        asElement().className = (baseStyle + " " + fadeStyle + " " + topStyle + " " + showStyle);
    }

    @Override
    public void setLeftOrientation() {
        asElement().className = (baseStyle + " " + fadeStyle + " " + leftStyle + " " + showStyle);
    }

    @Override
    public void setRightOrientation() {
        asElement().className = (baseStyle + " " + fadeStyle + " " + rightStyle + " " + showStyle);
    }

    @Override
    public void calculatePositionBottom(HTMLElement owner) {
        double top = owner.getBoundingClientRect().bottom + scrollY();
        double left = (owner.getBoundingClientRect().left - asElement().offsetWidth / 2 + owner.offsetWidth / 2);
        setPosition(top, left);
    }

    @Override
    public void calculatePositionTop(HTMLElement owner) {
        double top = owner.getBoundingClientRect().top + scrollY() - asElement().offsetHeight;
        double left = owner.getBoundingClientRect().left - asElement().offsetWidth / 2 + owner.offsetWidth / 2;
        setPosition(top, left);
    }

    @Override
    public void calculatePositionLeft(HTMLElement owner) {
        double top = owner.getBoundingClientRect().top + scrollY() - asElement().offsetHeight / 2 + owner.offsetHeight / 2;
        double left = (int) owner.getBoundingClientRect().left - asElement().offsetWidth;
        setPosition(top, left);
    }

    @Override
    public void calculatePositionRight(HTMLElement owner) {
        double top = owner.getBoundingClientRect().top + scrollY() - asElement().offsetHeight / 2 + owner.offsetHeight / 2;
        double left = owner.getBoundingClientRect().right;
        setPosition(top, left);
    }

    private double scrollY() {
        return DomGlobal.window.scrollY;
    }

    @Override
    public void setContent(String text) {
        contentPanel.textContent = text;
    }

    @Override
    public void setContent(HTMLElement element) {
        contentPanel.appendChild(element);
    }


    private void setPosition(double top, double left) {
        asElement().style.top = top + "px";
        asElement().style.left = left + "px";
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    TooltipViewSlots slots = new TooltipViewSlots() {
        @Override
        public HTMLElement getContent() {
            return contentPanel;
        }
    };

    @Override
    public V getViewSlots() {
        return (V) slots;
    }
}
