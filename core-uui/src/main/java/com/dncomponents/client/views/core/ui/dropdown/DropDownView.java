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

package com.dncomponents.client.views.core.ui.dropdown;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;


public interface DropDownView extends View {
    void addClickOnButton(BaseEventListener handler);

    void showList(boolean b);

    void addItem(IsElement item);

    void removeItem(IsElement item);

    void setButtonHtmlContent(HTMLElement content);

    IsElement getRelativeElement();

    void setButtonContent(String content);

    HandlerRegistration addClickOutOfButton(ClickHandler clickHandler);

    void addDropDownPanel(IsElement dropDownPanel);
}
