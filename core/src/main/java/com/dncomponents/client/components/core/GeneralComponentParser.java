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

package com.dncomponents.client.components.core;


import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class GeneralComponentParser<T extends IsElement> implements IsElementHtmlParser<T> {

    private final String tag;
    private Supplier<T> createComp = () -> (T) (IsElement<HTMLElement>) () -> DomUtil.createDiv();
    private Function<Props, T> functionComp;

    public GeneralComponentParser(String tag, Supplier<T> createComp) {
        this.tag = tag;
        this.createComp = createComp;
    }

    public GeneralComponentParser(String tag, Function<Props, T> fn) {
        this.tag = tag;
        this.functionComp = fn;
    }

    @Override
    public String getId() {
        return tag;
    }

    @Override
    public T parse(Element htmlElement, Map<String, ?> elements) {
        return replaceAndCopy(htmlElement, createComp.get());
    }

    @Override
    public T parse(Element htmlElement, Map<String, ?> elements, Props props) {
        if (functionComp != null) {
            return replaceAndCopy(htmlElement, functionComp.apply(props));
        } else {
            return replaceAndCopy(htmlElement, createComp.get());
        }
    }

}