package com.dncomponents.bootstrap.client.form;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.form.FormItemView;
import com.dncomponents.client.views.core.ui.form.FormUi;
import com.dncomponents.client.views.core.ui.form.FormView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class FormUiImpl implements FormUi {
    @UiField
    HTMLTemplateElement form;
    @UiField
    HTMLTemplateElement formItem;

    FormView rootView;

    HtmlBinder uiBinder = HtmlBinder.get(FormUiImpl.class, this);


    public FormUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public FormItemView getFormItemView() {
        return new FormItemViewImpl(formItem);
    }

    @Override
    public FormView getRootView() {
        if (rootView == null)
            rootView = new FormViewImpl(form);
        return rootView;
    }
}
