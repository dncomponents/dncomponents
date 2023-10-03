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

package com.dncomponents.client.components.core;

import elemental2.dom.CustomEvent;
import elemental2.dom.CustomEventInit;
import elemental2.dom.Element;
import elemental2.dom.Event;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author nikolasavic
 */
public class State {
    TemplateParser templateParser;
    Object value;
    boolean needsUpdate;
    String stateName;
    Supplier createValue;

    public State(String stateName, TemplateParser parser) {
        this.stateName = stateName;
        this.templateParser = parser;
    }

    public State(String stateName, Supplier createValue, TemplateParser parser) {
        this.stateName = stateName;
        this.templateParser = parser;
        this.createValue = createValue;
    }

    public void setValue() {
        Object oldValue = getValue();
        this.value = createValue.get();
        if (!Objects.equals(this.value, oldValue)) {
            needsUpdate = true;
        }
        if (this.value instanceof Collection) {
            //todo compare deep arrays,
            needsUpdate = true;
        }
    }

    public Object getValue() {
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
        }
    }

    public void forceUpdateUI() {
        updateUI(value);
        needsUpdate = false;
    }

}
