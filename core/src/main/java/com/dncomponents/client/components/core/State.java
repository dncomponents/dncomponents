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

import java.util.Objects;

/**
 * @author nikolasavic
 */
public class State<V> extends AbstractValueChangeHandler<V> {
    HtmlBinder binder;
    V value;
    String valueName;

    public State(V value, String valueName, HtmlBinder binder) {
        this.binder = binder;
        this.valueName = valueName;
        setValue(value);
    }

    public void setValue(V value) {
        setValue(value, true);
    }

    @Override
    public void setValue(V value, boolean fireEvents) {
        V oldValue = getValue();
        this.value = value;
        if (!Objects.equals(this.value, oldValue)) {
            if (fireEvents)
                ValueChangeEvent.fire(this, this.value, oldValue);
            updateUI(value);
        }
    }

    public V getValue() {
        return value;
    }

    protected void updateUI(V value) {
        this.binder.getTemplate().updateValueUi(valueName, value + "");
    }


}
