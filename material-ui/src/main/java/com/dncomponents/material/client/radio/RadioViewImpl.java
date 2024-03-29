package com.dncomponents.material.client.radio;

import com.dncomponents.Component;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.radio.RadioView;
import com.dncomponents.material.client.checkbox.CheckBoxViewImpl;
import elemental2.dom.Event;
import elemental2.dom.EventInit;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.MouseEvent;
/**
 * @author nikolasavic
 */
@Component
public class RadioViewImpl extends CheckBoxViewImpl implements RadioView {

    public static final String VIEW_ID = "default";

    HtmlBinder uiBinder = HtmlBinder.create(RadioViewImpl.class, this);

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
}
