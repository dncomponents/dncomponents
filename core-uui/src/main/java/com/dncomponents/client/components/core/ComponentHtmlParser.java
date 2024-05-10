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

import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;
import java.util.Set;

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
public interface ComponentHtmlParser extends IsElementHtmlParser<BaseComponent> {

    String VIEW = "view";
    String TEMPLATE_ID = "template-id";

    String ITEM_ID = "itemId";
    String CONTENT = "content";
    String ICON = "icon";

    String ITEM = "item";

    default ItemId getIdItem(Element element) {
        ItemId idItem = new ItemId();
        idItem.setId(getElementId(element));
        idItem.setContent(element.innerHTML);
        if (element.hasAttribute(CONTENT)) {
            idItem.setContent(element.getAttribute(CONTENT));
        }
        if (element.hasAttribute(ICON)) {
            idItem.setIcon(element.getAttribute(ICON));
        }
        return idItem;
    }

    default String getElementId(Element element) {
        if (element.hasAttribute(ITEM_ID))
            return element.getAttribute(ITEM_ID);
        else
            return null;
    }


    default <T extends View> T getView(Class viewClazz, Element html, Map<String, ?> elements) {
        String viewAttr = html.getAttribute(VIEW);
        String templateId = html.getAttribute(TEMPLATE_ID);
        if (viewAttr == null)
            viewAttr = "default";

        HTMLTemplateElement template = null;

        if (elements.containsKey(templateId))
            template = (HTMLTemplateElement) elements.get(templateId);

        Set<ViewFactory> viewFactories = Ui.get().getRegisteredViewFactoriesList().get(viewClazz);
        if (viewFactories == null) {
            DomGlobal.console.log("No ViewFactory for view: " + viewClazz);
            return null;
        }
        Object view = null;
        for (ViewFactory viewFactory : viewFactories) {
            if (viewFactory.getId().equalsIgnoreCase(viewAttr)) {
                view = viewFactory.getView(DomUtil.getAllAttributes(html), template);
            }
        }
        return (T) view;
    }

}
