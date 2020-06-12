package com.dncomponents.material.client.checkbox;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.OnChangeHandler;
import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.MainViewSlotsImpl;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import elemental2.dom.*;

public class CheckBoxViewImpl implements CheckBoxView {

    public static final String VIEW_ID = "default";

    @UiField
    public HTMLElement root;
    @UiField
    public HTMLInputElement checkBoxInput;
    @UiField
    public HTMLElement labelText;

    HtmlBinder uiBinder = HtmlBinder.get(CheckBoxViewImpl.class, this);


    public CheckBoxViewImpl() {
    }

    public CheckBoxViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }

    public CheckBoxViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    private void init() {
        DomUtil.addHandler(labelText, new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                if (isDisabled()) {
                    return;
                }
                setIndeterminate(false);
                checkBoxInput.checked = !checkBoxInput.checked;
                EventInit eventInit = EventInit.create();
                eventInit.setBubbles(true);
                checkBoxInput.dispatchEvent(new Event("change", eventInit));
            }
        });

    }

    public void setIndeterminate(boolean b) {
        checkBoxInput.indeterminate = b;
    }

    @Override
    public boolean isChecked() {
        return checkBoxInput.checked;
    }

    @Override
    public void setChecked(Boolean b) {
        checkBoxInput.checked = b == null ? false : b;
    }

    @Override
    public void setLabel(String html) {
        labelText.innerHTML = html;
    }

    @Override
    public String getLabel() {
        return labelText.textContent;
    }

    @Override
    public HandlerRegistration addOnChangeHandler(OnChangeHandler changeHandler) {
        return changeHandler.addTo(asElement());
    }

    @Override
    public void setName(String nameOfGroup) {
        checkBoxInput.setAttribute("name", nameOfGroup);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    MainViewSlots viewSlots;

    @Override
    public MainViewSlots getViewSlots() {
        if (viewSlots == null)
            viewSlots = new MainViewSlotsImpl(labelText);
        return viewSlots;
    }

    @Override
    public HTMLElement getFocusElement() {
        return checkBoxInput;
    }

}
