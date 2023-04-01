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

import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;


/**
 * Used to parse dn-components from html templates.
 * As a result, tag of component is replaced with its markup.
 * All the attributes set to dn tag are copied to root element of resulting markup.
 * "class" attribute replaces class and "addclass" adds class to existing class.
 *
 * <p>
 * e.g
 * <p>
 * <dn-text-box addclass="w-100"></dn-text-box>
 * <p>
 * becomes:
 * <input class="form-control w-100" type="text" >
 * <p>
 * <dn-text-box class="textClass" size="40"></dn-text-box>
 *
 * <input class="textClass" size="40">
 */
public interface IsElementHtmlParser<T extends IsElement> extends HtmlParser<T> {

    static void addStyle(Element element1, HTMLElement element2) {
        String style = element1.getAttribute("addstyle");
        if (style != null) {
            String newStyle = style;
            final String style2 = element2.getAttribute("style");
            if (style2 != null) {
                newStyle += style2;
            }
            element2.setAttribute("style", newStyle);
            element2.removeAttribute("addstyle");
        }
    }

    static void addClass(Element element1, Element element2) {
        String style = element1.getAttribute("addclass");
        if (style != null) {
            if (style.contains(" ")) {
                final String[] words = style.split(" ");
                for (String word : words) {
                    if (!word.isEmpty())
                        element2.classList.add(word);
                }
            } else {
                element2.classList.add(style);
            }
            element2.removeAttribute("addclass");
        }
    }

    default T replaceAndCopy(Element element1, T element2) {
        DomUtil.copyAllAttributes(element1, element2.asElement());
        element1.replaceWith(element2.asElement());
        addClass(element1, element2.asElement());
        addStyle(element1, element2.asElement());
        return element2;
    }

    default T replaceAndCopy(Element element1, T element2, String... removeAttributes) {
        for (String att : removeAttributes) {
            element1.removeAttribute(att);
        }
        return replaceAndCopy(element1, element2);
    }

}
