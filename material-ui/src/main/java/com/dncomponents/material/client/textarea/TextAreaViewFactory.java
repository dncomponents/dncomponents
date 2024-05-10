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

package com.dncomponents.material.client.textarea;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.material.client.textarea.TextAreaViewImpl.TextAreaViewBuilder;
import com.dncomponents.material.client.textbox.TextBoxType;
import com.dncomponents.material.client.textbox.TextBoxViewImpl.TextBoxViewBuilder;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLTemplateElement;

import java.util.Collections;
import java.util.Map;

public class TextAreaViewFactory extends AbstractPluginHelper implements ViewFactory<TextAreaViewImpl> {


    private static TextAreaViewFactory instance;

    private TextAreaViewFactory() {
        arguments.put(TextAreaViewBuilder.typeId, TextBoxType.lookUp.toStringList());
        arguments.put(TextAreaViewBuilder.labelId, Collections.emptyList());
        arguments.put(TextAreaViewBuilder.helperTextId, Collections.emptyList());
        arguments.put(TextAreaViewBuilder.characterCounterId, Collections.emptyList());
    }

    public static TextAreaViewFactory getInstance() {
        if (instance == null)
            instance = new TextAreaViewFactory();
        return instance;
    }

    @Override
    public TextAreaViewImpl getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
        TextBoxType type = TextBoxType.lookUp.getValue(attributes.get(TextBoxViewBuilder.typeId));
        String label = attributes.get(TextAreaViewBuilder.labelId);
        String helperText = attributes.get(TextAreaViewBuilder.helperTextId);
        String characterCounterIdString = attributes.get(TextAreaViewBuilder.characterCounterId);
        int characterCounter = 0;
        if (characterCounterIdString != null) {
            try {
                characterCounter = Integer.parseInt(attributes.get(TextAreaViewBuilder.characterCounterId));
            } catch (Exception ex) {
                DomGlobal.console.log("Error parsing characterCounterId");
            }
        }
        return TextAreaViewBuilder.get()
                .setType(type)
                .setLabel(label)
                .setHelperText(helperText)
                .setCharacterCounter(characterCounter)
                .build();
    }

    @Override
    public String getId() {
        return TextAreaViewImpl.VIEW_ID;
    }

    @Override
    public Class getClazz() {
        return TextAreaViewImpl.class;
    }

}