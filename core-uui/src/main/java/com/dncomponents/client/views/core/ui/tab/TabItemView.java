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

package com.dncomponents.client.views.core.ui.tab;

import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;


public interface TabItemView extends View, HasViewSlots<TabItemViewSlots> {

    void addItemSelectedHandler(EventListener handler);

    void select(boolean b);

    void setItemTitle(String text);

    void setItemTitleHtml(String html);

    void setItemTitle(HTMLElement html);

    void setItemContent(String html);

    void setItemContent(HTMLElement htmlElement);

    void setImmediate(Command command);

    HTMLElement getTabItemNav();

    HTMLElement getTabItemContent();
}
