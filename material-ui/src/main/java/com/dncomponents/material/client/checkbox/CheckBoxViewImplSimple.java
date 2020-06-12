package com.dncomponents.material.client.checkbox;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.OnChangeHandler;
import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.MainViewSlotsImpl;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class CheckBoxViewImplSimple implements CheckBoxView {

    public static final String VIEW_ID = "simple";

    @UiField
    public HTMLInputElement root;

    HtmlBinder uiBinder = HtmlBinder.get(CheckBoxViewImplSimple.class, this);

    public CheckBoxViewImplSimple() {
        uiBinder.bind();
    }

    public CheckBoxViewImplSimple(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public CheckBoxViewImplSimple(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    public void setIndeterminate(boolean b) {
        root.indeterminate = b;
    }

    @Override
    public boolean isChecked() {
        return root.checked;
    }

    @Override
    public void setChecked(Boolean b) {
        root.checked = b == null ? false : b;
    }

    @Override
    public void setLabel(String html) {
    }

    @Override
    public HandlerRegistration addOnChangeHandler(OnChangeHandler changeHandler) {
        return changeHandler.addTo(asElement());
    }

    @Override
    public void setName(String nameOfGroup) {
        root.setAttribute("name", nameOfGroup);
    }

    @Override
    public String getLabel() {
        return "";
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    MainViewSlots viewSlots = new MainViewSlotsImpl(DomUtil.createDiv());

    @Override
    public MainViewSlots getViewSlots() {
        return viewSlots;
    }
}