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


import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.dom.DomUtil;
import elemental2.dom.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class HtmlBinder<T> {

    protected T d;

    protected TemplateParser template;

    protected static List<String> allCss = new ArrayList<>();
    private static String defaultCssFile = "app.css";

    /**
     * This method enables css editing during development!
     * It removes generated app.css file and inject css in header within <style></style> tag.
     * This way compiling java files reflects changes on css.
     * For production deployment please don't use this method
     */
    public static void cssDevMode() {
        removeCssFile(defaultCssFile);
        if (!allCss.isEmpty()) {
            HTMLElement styleElement = DomUtil.createElement("style");

            // Set the CSS text
            styleElement.textContent = (allCss.stream().collect(Collectors.joining("\n")));
            // Append the <style> element to the <head> of the document

            DomGlobal.document.head.appendChild(styleElement);
        }
    }

    private static void removeCssFile(String href) {
        // Find the existing link element with the given href
        NodeList<Element> linkElements = DomGlobal.document.querySelectorAll("link[rel='stylesheet'][href='" + href + "']");
        // Remove each found link element
        for (int i = 0; i < linkElements.getLength(); i++) {
            HTMLElement element = (HTMLElement) linkElements.item(i);
            element.parentNode.removeChild(element);
        }
    }


    private String clazz;

    public static <T, C extends HtmlBinder<T>> C create(Class clazz, T view) {
        if (clazz == null || view == null)
            throw new IllegalArgumentException("Arguments can't be null!");
        final Supplier<HtmlBinder> htmlBinderSupplier = TemplateService.binders.get(clazz.getName());
        HtmlBinder binder = null;
        if (htmlBinderSupplier != null) {
            binder = htmlBinderSupplier.get();
        }
        if (binder == null)
            DomGlobal.console.log(clazz.getName() + " ---- " + " is null");
        if (binder == null)
            throw new IllegalStateException("Can't find corresponding binder for " + clazz.getName() + ".");
        binder.d = view;
        binder.clazz = clazz.getName();
        if (binder.template != null) {
            binder.template.clazz = clazz.getName();
        }
        return (C) binder;
    }

    public void setD(T d) {
        this.d = d;
    }

    public abstract void bind();

    public void bindAndUpdateUi() {
        bind();
        updateUi();
    }


    public void updateUi() {
        template.updateAll();
    }

    public void updateUiFieldsExcept(String... stateNames) {
        template.updateAll(true, stateNames);
    }

    public void updateUiFields(String... stateNames) {
        template.updateAll(false, stateNames);
    }

    public void watch(String stateName, ValueChangeHandler valueChangeHandler) {
        getState(stateName).addValueChangeHandler(valueChangeHandler);
    }

    public State getState(String stateName) {
        return template.getState(stateName);
    }


    /**
     * Use it to bind only fields from the current class without parent's fields.
     * <p>
     * If you want to bind all the fields including parent's fields,
     * provide complete template and use bind() instead.
     */
    public void bindThis() {
    }

    public void setTemplateContent(String templateContent) {
        template = new TemplateParser(templateContent);
        template.clazz = clazz;
    }

    public void setTemplateElement(HTMLTemplateElement templateElement) {
        if (templateElement == null)
            throw new NullPointerException("Template element can't be null!");
        template = new TemplateParser(templateElement);
        template.clazz = clazz;
    }

    public Node asNode() {
        return template.getCloned();
    }

    public HTMLElement getRoot() {
        return template.getRoot();
    }

    TemplateParser getTemplate() {
        return template;
    }
}
