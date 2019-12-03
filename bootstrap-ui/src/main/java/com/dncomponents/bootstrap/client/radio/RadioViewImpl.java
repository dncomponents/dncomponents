package com.dncomponents.bootstrap.client.radio;


import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.checkbox.CheckBoxViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxViewSlots;
import com.dncomponents.client.views.core.ui.radio.RadioView;
import elemental2.dom.*;

/**
 * @author nikolasavic
 */
@UiTemplate
public class RadioViewImpl extends CheckBoxViewImpl implements RadioView {

    HtmlBinder uiBinder = HtmlBinder.get(RadioViewImpl.class, this);

    public RadioViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public RadioViewImpl(HTMLTemplateElement templateElement) {
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
