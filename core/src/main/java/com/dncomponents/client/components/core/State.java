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

import com.dncomponents.client.components.core.events.value.AbstractValueChangeHandler;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author nikolasavic
 */
public class State<V> extends AbstractValueChangeHandler<V> {
    TemplateParser templateParser;
    V value;
    boolean pending;
    boolean needsUpdate;
    String valueName;
    Map<String, Function<V, ?>> functionMap = new HashMap<>();

    public State(String valueName, TemplateParser parser) {
        this.valueName = valueName;
        this.templateParser = parser;
    }

    public State(V value, String valueName, HtmlBinder binder) {
        this(value, valueName, binder.template);
    }

    public State(V value, String valueName, TemplateParser templateParser) {
        this.valueName = valueName;
        this.templateParser = templateParser;
        setValue(value);
    }

    public State(V value, String valueName, TemplateParser templateParser, Map<String, Function<V, ?>> functionMap) {
        this.valueName = valueName;
        this.templateParser = templateParser;
        this.functionMap = functionMap;
        setValue(value);
    }

    public void setValue(V value) {
        setValue(value, false);
    }

    @Override
    public void setValue(V value, boolean fireEvents) {
        V oldValue = getValue();
        this.value = value;
        if (!Objects.equals(this.value, oldValue)) {
            if (fireEvents)
                ValueChangeEvent.fire(this, this.value, oldValue);
            if (!pending) {
                updateUI(value);
                needsUpdate = false;
            } else {
                needsUpdate = true;
            }
        }
    }

    public V getValue() {
        return value;
    }

    protected void updateUI(V value) {
        this.templateParser.updateValueUi(valueName, value);
        functionMap.forEach((s, vFunction) ->
                templateParser.updateValueUi(s, vFunction.apply(value)));
    }

    public void addValueFunction(String key, Function<V, ?> valueFunction) {
        this.functionMap.put(key, valueFunction);
    }

    public void setFunctionMap(Map<String, Function<V, ?>> functionMap) {
        if (functionMap != null) {
            this.functionMap = functionMap;
        }
    }

    public Object getValueByName(String valueName) {
        if (this.valueName.equals(valueName)) {
            return value;
        }
        final Function<V, ?> vFunction = this.functionMap.get(valueName);
        if (vFunction != null) {
            return vFunction.apply(value);
        }
        return null;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public void updateUI() {
        if (needsUpdate) {
            updateUI(value);
        }
    }

    public void forceUpdateUI() {
        updateUI(value);
    }

}
