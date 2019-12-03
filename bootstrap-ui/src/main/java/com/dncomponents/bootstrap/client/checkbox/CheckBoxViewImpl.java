package com.dncomponents.bootstrap.client.checkbox;


import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.OnChangeHandler;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxViewSlots;
import com.google.gwt.event.shared.HandlerRegistration;
import elemental2.dom.*;

/**
 * @author nikolasavic
 */
@UiTemplate
public class CheckBoxViewImpl implements CheckBoxView {

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
    }

    public CheckBoxViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
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

    protected static final String VIEW_ID = "DEFAULT";


    CheckBoxViewSlots viewSlots = new CheckBoxViewSlots() {
        @Override
        public HTMLElement getMainSlot() {
            return labelText;
        }
    };

    @Override
    public CheckBoxViewSlots getViewSlots() {
        return viewSlots;
    }

    @Override
    public HTMLElement getFocusElement() {
        return checkBoxInput;
    }

}