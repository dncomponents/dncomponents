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

package com.dncomponents.client.components.core;

import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;

import java.util.Map;
import java.util.function.Supplier;

public class GeneralComponentParser<T extends IsElement> implements IsElementHtmlParser<T> {

    private final String tag;
    private final Supplier<T> createComp;

    public GeneralComponentParser(String tag, Supplier<T> createComp) {
        this.tag = tag;
        this.createComp = createComp;
    }

    @Override
    public String getId() {
        return tag;
    }

    @Override
    public T parse(Element htmlElement, Map<String, ?> elements) {
        return replaceAndCopy(htmlElement, createComp.get());
    }

}
