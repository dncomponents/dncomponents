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

package com.dncomponents.bootstrap.client.button;

import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.HasStyle;


public enum FontAwesomeSize implements HasStyle {
    EXTRA_SMALL("xs"), SMALL("sm"), LARGE("lg"),
    TWO_TIMES("2x"), THREE_TIMES("3x"), FIVE_TIMES("5x"), SEVENT_TIMES("6x");

    public static EnumLookUp<FontAwesomeSize> lookUp = new EnumLookUp<>(FontAwesomeSize.values());


    String style;

    FontAwesomeSize(String style) {
        this.style = FontAwesome.BASE_STYLE + "-" + style;
    }

    public String getStyle() {
        return style;
    }
}
