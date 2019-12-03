package com.dncomponents.client.components.core;

/**
 * @author nikolasavic
 */
public abstract class I18nBinder<T> {

    protected T i18n;

    public static <T> I18nBinder get(Class clazz, T i18n) {
        if (clazz == null || i18n == null)
            throw new IllegalArgumentException("Arguments can't be null!");
        I18nBinder binder = TemplateService.i18nBinder.get(clazz.getName());
        if (binder == null)
            throw new IllegalStateException("Can't find corresponding binder for " + clazz.getName() + ".");
        binder.i18n = i18n;
        return binder;
    }

    public abstract void bind();

}