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
import java.util.NoSuchElementException;

/**
 * <p>
 * Default name for translation class is AppMessages.
 * that means when you refere to this object in java code you call I18n.get().getValue("someProperty")
 * or from html file {{someProperty}}
 * <p>
 * If you want to add Translation you add sufix that begins with underscore:
 * e.g AppMessages_spanish.java and its properties file AppMessages_spanish.properties
 * <p>
 * If you have class other than AppMessages, e.g MyCustomMessages in java code you refer to this object
 * I18n.get("MyCustomMessages").getValue("someProperty")...
 * or from html file {{MyCustomMessages:someProperty}}
 */
public class DnI18n {

    public static String DEFAULT = "AppMessages";

    public static String START_TAG = "{[";
    public static String END_TAG = "]}";

    private Map<String, KeyValue> keyValues = new HashMap<>();

    //it contains unique list of default translation names and current I18n objects
    //e.g default name AppMessages can have different I18n objects
    //if you want to switch to e.g spanish version you register AppMessage_spanish i18n object
    //I18e.put(new AppMessage_spanish()); this will set AppMessages - AppMessage_spanish pair to lfiles
    private static Map<String, DnI18n> lfiles = new HashMap<>();


    private DnI18n fallback;

    private static final String SEPARATOR = ":";
    private static final String MORE_THAN = ">";
    private static final String LESS_THAN = "<";
    private static final String EQUALS = "=";

    private String name = this.getClass().getName();


    public void setName(String name) {
        this.name = name;
    }

    public DnI18n getFallback() {
        return fallback;
    }

    public void setFallback(DnI18n fallback) {
        this.fallback = fallback;
    }

    public String getValue(String key, Object... args) {
        KeyValue keyValue = keyValues.get(key);
        if (keyValue == null && fallback != null) {
            keyValue = fallback.keyValues.get(key);
        }
        if (keyValue == null)
            throw new NoSuchElementException(key + " not found. Check property file or it must be a typo?.");
        String value = keyValue.value;
        //if has items with rules
        if (!keyValue.items.isEmpty()) {
            value = keyValue.getRootKeyValue().value;
            for (KeyValue item : keyValue.getAllItems()) {
                if (item.rule == null)
                    continue;
                Integer orderNumberRule = Integer.parseInt(getBetween(item.rule, '{', '}'));
                int n = item.rule.indexOf('}');
                String operator = getBetween(item.rule, n, n + 2);
                Integer ruleValue = Integer.parseInt(getBetween(item.rule, n + 1, n + 3));
                if (orderNumberRule == null || (orderNumberRule != null && orderNumberRule >= args.length)
                    || operator == null || operator.isEmpty())
                    continue;
                if (operator.equals("=")) {
                    if (args[orderNumberRule].equals(ruleValue)) {
                        value = item.value;
                        break;
                    }
                }
            }
        }
        do {
            final String between = getBetween(value, '{', '}');
            if (between == null || between.trim().isEmpty())
                break;
            Integer orderNumb = Integer.parseInt(between);
            if (args == null || orderNumb >= args.length)
                break;
            value = value.replace("{" + orderNumb + "}", args[orderNumb] + "");
        } while (true);
        return value;
    }

    public void putValue(String key, KeyValue value) {
        keyValues.put(key, value);
    }

    public void putValue(String key, String value) {
        final KeyValue keyValue = new KeyValue(key, value);
        final String between = getBetween(key, '[', ']');
        String keyOrg = key;
        if (between != null && !between.isEmpty()) {
            keyOrg = key.substring(0, key.indexOf('['));
            keyValue.key = keyOrg;
            keyValue.rule = between;
        }
        KeyValue existing = keyValues.get(keyOrg);
        if (existing != null)
            existing.items.add(keyValue);
        else
            keyValues.put(keyOrg, keyValue);
    }


    static String getBetween(String text, Character c1, Character c2) {
        int index1 = text.indexOf(c1);
        int index2 = text.indexOf(c2);
        if (index1 != -1 && index2 != -1) {
            if (index2 > index1) {
                return text.substring(index1 + 1, index2);
            }
        }
        return null;
    }

    static String getBetween(String text, Integer index1, Integer index2) {
        try {
            return text.substring(index1 + 1, index2);
        } catch (Exception ex) {
            return null;
        }
    }

    static String getBetween(String text, String c1, String c2) {
        try {
            return text.substring(text.indexOf(c1) + c1.length(), text.indexOf(c2));
        } catch (Exception ex) {
            return null;
        }
    }

    private void replace(String text, String value) {
        text.replace(text, value);
    }

    public static DnI18n get(String s) {
        return lfiles.get(s);
    }

    public static DnI18n get() {
        return lfiles.get(DEFAULT);
    }

    public static String t(String key, Object... args) {
        return get().getValue(key, args);
    }

    public static void put(String name, DnI18n i18e) {
        lfiles.put(name, i18e);
    }

    public static void set(DnI18n i18e) {
        lfiles.put(checkName(i18e), i18e);
    }

    private static String checkName(DnI18n i18e) {
        String name = i18e.getClass().getSimpleName();
        if (name.contains("_")) {
            name = name.substring(0, name.indexOf('_'));
        }
        return name;
    }
}
