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

import java.util.HashMap;
import java.util.Map;

public class Props {

    public <P extends Props> P wrap(Props prop, HtmlBinder binder) {
        this.statesMap = prop.statesMap;
        this.stringsMap = prop.stringsMap;
        updateUiOnChange(binder);
        return (P) this;
    }

    private Map<String, State> statesMap = new HashMap<>();
    private Map<String, String> stringsMap = new HashMap<>();

    public String getAttribute(String name) {
        String attr = stringsMap.get(name.toLowerCase());
        return attr != null ? attr : null;
    }

    public <T> State<T> getState(String name) {
        return statesMap.get(name);
    }

    public <T> T getStateValue(String name) {
        State state = statesMap.get(name.toLowerCase());
        return state != null ? (T) state.getValue() : null;
    }

    void put(String key, State value) {
        statesMap.put(key.toLowerCase(), value);
    }

    void putAttribute(String key, String value) {
        stringsMap.put(key.toLowerCase(), value);
    }

    public Map<String, State> getStatesMap() {
        return statesMap;
    }

    public Map<String, String> getStringsMap() {
        return stringsMap;
    }

    public void updateUiOnChange(HtmlBinder binder) {
        State.watch(event -> binder.updateUi(), statesMap.values().toArray(new State[0]));
    }
}
