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

package com.dncomponents.client.components.dropdown;

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.CanSelect;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemView;
import elemental2.dom.HTMLElement;


public class DropDownItem<T> extends BaseComponent<T, DropDownItemView> implements CanSelect {

    DropDown<T> dropDown;
    private boolean selected;

    public DropDownItem(DropDown<T> dropDown, T userObject) {
        super(dropDown.getView().getDropDownItemView());
        this.dropDown = dropDown;
        setRenderer(dropDown.renderer);
        setUserObject(userObject);
        init();
    }


    public DropDownItem(DropDownItemView view) {
        super(view);
    }

    private void init() {
        view.addClickHandler(mouseEvent ->
                dropDown.setSelected(DropDownItem.this, !DropDownItem.this.isSelected(), true));
    }

    public void setContent(String content) {
        view.setContent(content);
    }

    public void setContent(HTMLElement content) {
        view.setHtmlContent(content);
    }

    @Deprecated
    public boolean isActive() {
        return selected;
    }

    @Deprecated
    public void setActive(boolean active) {
        this.selected = active;
        view.setActive(active);
    }

    public static String is() {
        return "dropdown-item-dn";
    }

    @Override
    public void setSelected(boolean b) {
        this.selected = b;
        view.setActive(selected);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }


    public void setRenderer(MainRenderer<T> renderer) {
        super.setRendererBase(renderer);
    }
}
