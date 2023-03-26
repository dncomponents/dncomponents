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

package com.dncomponents.client.components.pager;

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.pager.PagerItemView;
import elemental2.dom.MouseEvent;

/**
 * @author nikolasavic
 */
public class PagerItem extends BaseComponent<Integer, PagerItemView> {

    public PagerItem(Pager pager, Integer number) {
        super(pager.getView().getPagerItemView());
        this.setUserObject(number);
        setText(((userObject == -1) ? "..." : userObject + 1) + "");
        new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                pager.setCurrentPage(getUserObject());
            }
        }.addTo(asElement());
    }

    public PagerItem(Pager pager, String text, ClickHandler clickHandler) {
        super(pager.getView().getPagerItemView());
        setText(text);
        clickHandler.addTo(asElement());
    }

    public void setText(String str) {
        view.setText(str);
    }

    public void setActive(boolean b) {
        view.setActive(b);
    }

}
