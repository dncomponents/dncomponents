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

package com.dncomponents.client.views.core.ui.tooltip;

import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public interface TooltipView<V extends TooltipViewSlots> extends View, HasViewSlots<V> {

    void setBottomOrientation();

    void setTopOrientation();

    void setLeftOrientation();

    void setRightOrientation();

    void calculatePositionBottom(HTMLElement owner);

    void calculatePositionTop(HTMLElement owner);

    void calculatePositionLeft(HTMLElement owner);

    void calculatePositionRight(HTMLElement owner);

    void setContent(String text);

    void setContent(HTMLElement element);


}
