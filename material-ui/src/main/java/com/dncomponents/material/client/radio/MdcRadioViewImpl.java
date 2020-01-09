package com.dncomponents.material.client.radio;

import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxViewSlots;
import com.dncomponents.client.views.core.ui.radio.RadioView;
import com.dncomponents.material.client.checkbox.MdcCheckBoxViewImpl;
import elemental2.dom.*;

@UiTemplate
public class MdcRadioViewImpl extends MdcCheckBoxViewImpl implements RadioView {

    HtmlBinder uiBinder = HtmlBinder.get(MdcRadioViewImpl.class, this);

    public MdcRadioViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public MdcRadioViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        DomUtil.addHandler(labelText, new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                if (isDisabled()) {
                    return;
                }
                if (!checkBoxInput.checked) {
                    checkBoxInput.checked = true;
                    EventInit eventInit = EventInit.create();
                    eventInit.setBubbles(true);
                    checkBoxInput.dispatchEvent(new Event("change", eventInit));
                }
            }
        });
    }

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

}