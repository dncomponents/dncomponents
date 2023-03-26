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

import elemental2.dom.HTMLTemplateElement;

public abstract class HtmlBinder<T> {

    protected T tw;

    protected TemplateParser template;

    private String clazz;

    public static <T> HtmlBinder get(Class clazz, T view) {
        if (clazz == null || view == null)
            throw new IllegalArgumentException("Arguments can't be null!");
        HtmlBinder binder = TemplateService.binders.get(clazz.getName());
        if (binder == null)
            throw new IllegalStateException("Can't find corresponding binder for " + clazz.getName() + ".");
        binder.tw = view;
//        if (!binder.tw.getClass().equals(clazz))
//            throw new IllegalStateException("Wrong binder class! Expects " +
//                    "HtmlBinder.get(" + view.getClass() + ".class, this);");
        binder.clazz = clazz.getName();
        if (binder.template != null) {
            binder.template.clazz = clazz.getName();
        }
        return binder;
    }

    public void setTw(T tw) {
        this.tw = tw;
    }

    public abstract void bind();

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

    public TemplateParser getTemplate() {
        return template;
    }

}
