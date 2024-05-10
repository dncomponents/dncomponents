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

package com.dncomponents.client.views.core.ui.modal;

import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;


public interface DialogView extends View, HasViewSlots<DialogViewSlots> {

    void setHeader(IsElement element);

    void setContent(HTMLElement element);

    void setFooter(IsElement element);

    void setTitle(String title);

    void addCloseHandler(Command onCloseCmd);

    void addOkHandler(ClickHandler clickHandler, String text);

    void show();

    void hide();

    void addFooterElement(HTMLElement element);

    void clearFooter();

    void setWidth(String width);

    void setHeight(String height);

    void setBackDrop(boolean backdrop);

    void setCloseOnEscape(boolean closeOnEscape);

    void setDraggable(boolean draggable);

    void setPosition(int top, int left);

    int getTopPosition();

    int getLeftPosition();
}
