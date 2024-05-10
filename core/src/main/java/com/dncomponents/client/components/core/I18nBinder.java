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

import java.util.function.Supplier;


public abstract class I18nBinder<T> {

    protected T i18n;

    public static <T> I18nBinder get(Class clazz, T i18n) {
        if (clazz == null || i18n == null)
            throw new IllegalArgumentException("Arguments can't be null!");
        final Supplier<I18nBinder> i18nBinderSupplier = TemplateService.i18nBinder.get(clazz.getName());
        I18nBinder binder = null;
        if (i18nBinderSupplier != null) {
            binder = i18nBinderSupplier.get();
        }
        if (binder == null)
            throw new IllegalStateException("Can't find corresponding binder for " + clazz.getName() + ".");
        binder.i18n = i18n;
        return binder;
    }

    public abstract void bind();

}
