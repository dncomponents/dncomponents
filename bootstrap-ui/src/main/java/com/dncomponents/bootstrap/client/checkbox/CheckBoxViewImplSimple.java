package com.dncomponents.bootstrap.client.checkbox;


import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.OnChangeHandler;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxViewSlots;
import com.google.gwt.event.shared.HandlerRegistration;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
//language=html
@UiTemplate("<input ui-field=\"root\" class=\"form-check-input\" type=\"checkbox\" value=\"\">")
public class CheckBoxViewImplSimple implements CheckBoxView {

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
        changeHandler.addTo(asElement());
        return null;
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

    protected static final String VIEW_ID = "SIMPLE";

    CheckBoxViewSlots viewSlots = new CheckBoxViewSlots() {
        @Override
        public HTMLElement getMainSlot() {
            return DomUtil.createDiv();
        }
    };

    @Override
    public CheckBoxViewSlots getViewSlots() {
        return viewSlots;
    }
}