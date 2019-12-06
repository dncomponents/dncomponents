package com.dncomponents.client.components.core;

import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.IsElement;
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
public interface ComponentHtmlParser extends HtmlParser<BaseComponent> {

    String VIEW = "view";
    String TEMPLATE_ID = "template-id";

    String ITEM_ID = "itemId";
    String CONTENT = "content";

    String ITEM = "item";

    static void copyStyle(Element element1, Element element2) {
        String style = element1.getAttribute("class");
        if (style != null)
            element2.className = style;
    }

    static void addStyle(Element element1, Element element2) {
        String style = element1.getAttribute("addclass");
        if (style != null) {
            element2.classList.add(style);
            element2.removeAttribute("addclass");
        }
    }

    default void replaceAndCopy(Element element1, IsElement element2) {
        DomUtil.copyAllAttributes(element1, element2.asElement());
        DomUtil.replace(element2.asElement(), element1);
        addStyle(element1, element2.asElement());
        copyStyle(element1, element2.asElement());
    }

    default ItemId getIdItem(Element element) {
        ItemId idItem = new ItemId();
        idItem.setId(getElementId(element));
        idItem.setHtml(element.innerHTML);
        if (element.hasAttribute(CONTENT)) {
            idItem.setHtml(element.getAttribute(CONTENT));
        }
        return idItem;
    }

    default String getElementId(Element element) {
        if (element.hasAttribute(ITEM_ID))
            return element.getAttribute(ITEM_ID);
        else
            return null;
    }

    default <T extends View> T getViewFor(String viewClazz, Element html, Map<String, ?> elements) {
        String viewAttr = html.getAttribute(VIEW);
        String templateId = html.getAttribute(TEMPLATE_ID);

        if (viewAttr == null)
            viewAttr = "default";

        HTMLTemplateElement template = null;

        if (elements.containsKey(templateId))
            template = (HTMLTemplateElement) elements.get(templateId);

        ViewFactory viewFactory = Ui.get().getRegisteredViewFactoriesMap().get(viewClazz);
        if (viewFactory == null) {
            DomGlobal.console.log("No ViewFactory for view: " + viewClazz);
            return null;
        }
        Object view = null;
        if (viewFactory.getId().equalsIgnoreCase(viewAttr)) {
            view = viewFactory.getView(DomUtil.getAllAttributes(html), template);
        }
        return (T) view;
    }


    //    @Deprecated
    default <T extends View> T getView(Class viewClazz, Element html, Map<String, ?> elements) {
        String viewAttr = html.getAttribute(VIEW);
        String templateId = html.getAttribute(TEMPLATE_ID);
        DomGlobal.console.log("for: " + viewClazz);
        if (viewAttr == null)
            viewAttr = "default";

        HTMLTemplateElement template = null;

        if (elements.containsKey(templateId))
            template = (HTMLTemplateElement) elements.get(templateId);

        DomGlobal.console.log("template: " + template);

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

    default boolean isPremium() {
        return false;
    }
}