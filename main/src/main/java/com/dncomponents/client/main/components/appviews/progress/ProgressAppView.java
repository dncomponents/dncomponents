/*
 * Copyright 2024 dncomponents
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

package com.dncomponents.client.main.components.appviews.progress;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.progress.Progress;
import com.dncomponents.client.components.textarea.TextArea;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLElement;


public class ProgressAppView implements IsElement {
    @UiField
    HTMLElement root;
    @UiField
    public Progress eventProgress;
    @UiField
    public TextArea eventsTa;

    public ProgressAppView() {
        HtmlBinder.create(ProgressAppView.class, this).bind();
        initEvents();
    }

    private int percent = 0;

    private void initEvents() {
        DomGlobal.setInterval(e -> {
            if (percent > 100)
                percent = 0;

            eventProgress.setValue(percent += 10, true);
        }, 1000);
        eventProgress.addValueChangeHandler((ValueChangeEvent<Integer> event) -> eventsTa.setValue(event.getValue() + "%"));
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    private static ProgressAppView instance;

    public static ProgressAppView getInstance() {
        if (instance == null) instance = new ProgressAppView();
        return instance;
    }
}
