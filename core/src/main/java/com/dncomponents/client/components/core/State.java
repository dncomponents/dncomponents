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

package com.dncomponents.client.components.core;


import com.dncomponents.client.components.core.events.AbstractHandler;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.value.HasValueChangeHandlers;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.dom.DomUtil;

import java.util.function.Supplier;


public class State<T> extends AbstractHandler implements HasValueChangeHandlers<T> {
    static int ids = 0;
    int id = ids++;
    TemplateParser templateParser;
    T value;
    boolean needsUpdate;
    String stateName;
    Supplier<T> createValue;

    State(String stateName, TemplateParser parser) {
        this.stateName = stateName;
        this.templateParser = parser;
    }

    State(String stateName, Supplier createValue, TemplateParser parser) {
        this.stateName = stateName;
        this.templateParser = parser;
        this.createValue = createValue;
    }


    String json = "";

    void setValue() {
        this.value = createValue.get();
        String stringify = DomUtil.stringifyFlatted(value);
        if (!stringify.equals(json)) {
            needsUpdate = true;
            json = stringify;
        }
    }

    public T getValue() {
        return value;
    }

    public String getStateName() {
        return stateName;
    }

    private void updateUI(Object value) {
        this.templateParser.updateValueUi(stateName, value);
    }

    public void updateUI() {
        if (needsUpdate) {
            updateUI(value);
            needsUpdate = false;
            ValueChangeEvent.fire(this, value);
        }
    }

    public void forceUpdateUI() {
        updateUI(value);
        needsUpdate = false;
        ValueChangeEvent.fire(this, value);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
        return handler.addTo(ensureHandlers());
    }

    public static void watch(ValueChangeHandler valueChangeHandler, State... states) {
        for (State state : states) {
            state.addValueChangeHandler(valueChangeHandler);
        }
    }

}