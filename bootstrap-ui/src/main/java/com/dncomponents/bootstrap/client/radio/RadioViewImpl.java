/*
 * Copyright 2023 dncomponents
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

package com.dncomponents.bootstrap.client.radio;

import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.checkbox.CheckBoxViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.radio.RadioView;
import elemental2.dom.Event;
import elemental2.dom.EventInit;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.MouseEvent;

/**
 * @author nikolasavic
 */
@UiTemplate
public class RadioViewImpl extends CheckBoxViewImpl implements RadioView {

    public static final String VIEW_ID = "default";

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
}
